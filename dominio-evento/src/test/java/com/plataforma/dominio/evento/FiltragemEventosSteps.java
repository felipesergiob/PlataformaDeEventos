package com.plataforma.dominio.evento;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoService;
import com.plataforma.evento.EventoRepository;
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

    private List<Evento> eventosFiltrados;

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
        Mockito.when(eventoRepository.listarTodos()).thenReturn(eventos);
        
        Mockito.when(eventoRepository.listarPorGenero("Rock")).thenReturn(
            eventos.stream()
                .filter(evento -> evento.getGenero().equals("Rock"))
                .toList()
        );
        
        Mockito.when(eventoRepository.listarPorHorario(
            Mockito.any(LocalTime.class), 
            Mockito.any(LocalTime.class)
        )).thenReturn(eventos.stream()
            .filter(evento -> evento.getDataInicio().toLocalTime().equals(LocalTime.parse("20:00:00")))
            .toList());
        
        Mockito.when(eventoRepository.listarPorData(
            Mockito.any(LocalDate.class)
        )).thenReturn(eventos.stream()
            .filter(evento -> evento.getDataInicio().toLocalDate().equals(LocalDate.parse("2024-04-01")))
            .toList());
        
        Mockito.when(eventoRepository.listarPorPreco(
            Mockito.any(BigDecimal.class)
        )).thenAnswer(invocation -> {
            BigDecimal precoMaximo = invocation.getArgument(0);
            return eventos.stream()
                .filter(evento -> evento.getValor().compareTo(precoMaximo) <= 0)
                .toList();
        });
        
        Mockito.when(eventoRepository.listarPorValor(
            Mockito.any(BigDecimal.class),
            Mockito.any(BigDecimal.class)
        )).thenReturn(eventos);
    }

    @When("eu filtrar eventos por gênero {string}")
    public void euFiltrarEventosPorGenero(String genero) {
        eventosFiltrados = eventoRepository.listarPorGenero(genero);
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

    @Then("o evento deve ter o nome {string}")
    public void oEventoDeveTerONome(String nomeEsperado) {
        Assertions.assertTrue(eventosFiltrados.stream()
            .anyMatch(evento -> evento.getNome().equals(nomeEsperado)));
    }
} 