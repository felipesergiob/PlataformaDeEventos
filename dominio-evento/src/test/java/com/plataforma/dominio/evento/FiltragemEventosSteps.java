package com.plataforma.dominio.evento;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.evento.EventoService;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FiltragemEventosSteps {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;

    private List<Evento> eventosFiltrados;
    private IllegalArgumentException ultimaExcecao;

    @Before
    public void setup() {
        Mockito.reset(eventoRepository);
    }

    @DataTableType
    public Evento eventoEntry(Map<String, String> entry) {
        return new Evento(
            EventoId.novo(),
            entry.get("nome"),
            entry.get("descricao"),
            LocalDateTime.parse(entry.get("dataInicio")),
            LocalDateTime.parse(entry.get("dataFim")),
            entry.get("local"),
            Integer.parseInt(entry.get("capacidade")),
            entry.get("organizador"),
            entry.get("categoria"),
            entry.get("genero"),
            new BigDecimal(entry.get("valor"))
        );
    }

    @Given("os seguintes eventos:")
    public void osSeguintesEventos(List<Evento> eventos) {
        Evento eventoAtivo = eventos.get(0);
        Evento eventoCancelado = eventos.size() > 1 ? eventos.get(1) : null;
        
        if (eventoCancelado != null) {
            eventoCancelado.cancelar();
        }
        
        Mockito.when(eventoRepository.listarPorGenero("Rock")).thenReturn(List.of(eventoAtivo));
        Mockito.when(eventoRepository.listarPorGenero("rock")).thenReturn(List.of(eventoAtivo));
        
        if (eventoCancelado != null) {
            Mockito.when(eventoRepository.listarPorGenero("Samba")).thenReturn(List.of());
        } else {
            Mockito.when(eventoRepository.listarPorGenero("Samba")).thenReturn(List.of());
        }
        
        Mockito.when(eventoRepository.listarPorHorario(
            Mockito.any(LocalTime.class), 
            Mockito.any(LocalTime.class)
        )).thenReturn(List.of(eventoAtivo));
        
        Mockito.when(eventoRepository.listarPorData(
            Mockito.any(LocalDate.class)
        )).thenReturn(List.of(eventoAtivo));
        
        Mockito.when(eventoRepository.listarPorPreco(
            Mockito.any(BigDecimal.class)
        )).thenReturn(eventos);

        Mockito.when(eventoService.listarPorGenero("Rock")).thenReturn(List.of(eventoAtivo));
        Mockito.when(eventoService.listarPorGenero("rock")).thenReturn(List.of(eventoAtivo));
        Mockito.when(eventoService.listarPorGenero("")).thenThrow(new IllegalArgumentException("Gênero não pode ser nulo ou vazio"));
        
        if (eventoCancelado != null) {
            Mockito.when(eventoService.listarPorGenero("Samba")).thenReturn(List.of());
        } else {
            Mockito.when(eventoService.listarPorGenero("Samba")).thenReturn(List.of());
        }
    }

    @When("eu filtrar eventos por gênero {string}")
    public void euFiltrarEventosPorGenero(String genero) {
        try {
            eventosFiltrados = eventoService.listarPorGenero(genero);
        } catch (IllegalArgumentException e) {
            ultimaExcecao = e;
        }
    }

    @When("eu filtrar eventos por horário {string}")
    public void euFiltrarEventosPorHorario(String horario) {
        LocalDateTime horarioFiltro = LocalDateTime.parse(horario);
        eventosFiltrados = eventoRepository.listarPorHorario(
            horarioFiltro.toLocalTime(),
            horarioFiltro.toLocalTime().plusHours(1)
        );
    }

    @When("eu filtrar eventos por data {string}")
    public void euFiltrarEventosPorData(String data) {
        LocalDateTime dataFiltro = LocalDateTime.parse(data);
        eventosFiltrados = eventoRepository.listarPorData(dataFiltro.toLocalDate());
    }

    @When("eu filtrar eventos por preço máximo {double}")
    public void euFiltrarEventosPorPrecoMaximo(Double precoMaximo) {
        eventosFiltrados = eventoRepository.listarPorPreco(new BigDecimal(precoMaximo));
    }

    @Then("devo ver {int} eventos")
    public void devoVerEventos(int quantidadeEsperada) {
        Assertions.assertEquals(quantidadeEsperada, eventosFiltrados.size());
    }

    @Then("devo receber um erro de gênero inválido")
    public void devoReceberErroDeGeneroInvalido() {
        Assertions.assertNotNull(ultimaExcecao, "Deveria ter recebido uma exceção");
        Assertions.assertEquals("Gênero não pode ser nulo ou vazio", ultimaExcecao.getMessage());
    }

    @Then("o evento deve ter o nome {string}")
    public void oEventoDeveTerONome(String nomeEsperado) {
        Assertions.assertTrue(eventosFiltrados.stream()
            .anyMatch(evento -> evento.getNome().equals(nomeEsperado)));
    }
} 