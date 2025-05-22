package com.plataforma.dominio.evento;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoService;
import com.plataforma.inscricao.Inscricao;
import com.plataforma.inscricao.InscricaoId;
import com.plataforma.inscricao.InscricaoService;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class VisualizacaoCalendarioTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscricaoService inscricaoService;

    private UsuarioId usuarioId;
    private List<Evento> eventos;
    private List<Inscricao> inscricoes;
    private List<Evento> eventosFiltrados;

    @Before
    public void setup() {
        usuarioId = UsuarioId.novo();
        eventos = new ArrayList<>();
        inscricoes = new ArrayList<>();
        eventosFiltrados = new ArrayList<>();
        Mockito.reset(eventoService, inscricaoService);
    }

    @Dado("que existem os seguintes eventos:")
    public void queExistemOsSeguintesEventos(List<Map<String, String>> eventosData) {
        for (Map<String, String> eventoData : eventosData) {
            Evento evento = criarEvento(eventoData);
            eventos.add(evento);
            Mockito.when(eventoService.obter(evento.getId())).thenReturn(evento);
        }
        Mockito.when(eventoService.listarTodos()).thenReturn(eventos);
    }

    @E("que o usuário tem as seguintes inscrições:")
    public void queOUsuarioTemAsSeguintesInscricoes(List<Map<String, String>> inscricoesData) {
        for (Map<String, String> inscricaoData : inscricoesData) {
            int eventoIndex = Integer.parseInt(inscricaoData.get("eventoIndex")) - 1;
            Evento evento = eventos.get(eventoIndex);
            
            Inscricao inscricao = new Inscricao(
                InscricaoId.novo(),
                usuarioId,
                evento.getId(),
                LocalDateTime.now(),
                null,
                null
            );
            
            if ("CONFIRMADA".equals(inscricaoData.get("status"))) {
                inscricao.confirmar();
            } else if ("CANCELADA".equals(inscricaoData.get("status"))) {
                inscricao.cancelar();
            }
            
            inscricoes.add(inscricao);
        }
        
        Mockito.when(inscricaoService.listarInscricoesPorUsuario(usuarioId)).thenReturn(inscricoes);
        
        List<Evento> eventosConfirmados = inscricoes.stream()
            .filter(Inscricao::estaConfirmada)
            .map(inscricao -> eventos.stream()
                .filter(e -> e.getId().equals(inscricao.getEventoId()))
                .findFirst()
                .orElse(null))
            .filter(e -> e != null)
            .toList();
            
        Mockito.when(inscricaoService.listarEventosConfirmados(usuarioId)).thenReturn(eventosConfirmados);
    }

    @Quando("o usuário visualiza seu calendário")
    public void oUsuarioVisualizaSeuCalendario() {
        eventosFiltrados = inscricaoService.listarEventosConfirmados(usuarioId);
    }

    @Entao("o usuário deve ver {int} eventos confirmados")
    public void oUsuarioDeveVerEventosConfirmados(int quantidadeEsperada) {
        assertEquals(quantidadeEsperada, eventosFiltrados.size());
    }

    @Quando("o usuário filtra os eventos do período {string} até {string}")
    public void oUsuarioFiltraOsEventosDoPeriodo(String dataInicio, String dataFim) {
        LocalDateTime inicio = LocalDateTime.parse(dataInicio + "T00:00:00");
        LocalDateTime fim = LocalDateTime.parse(dataFim + "T23:59:59");
        
        eventosFiltrados = inscricaoService.listarEventosConfirmados(usuarioId).stream()
            .filter(evento -> evento.getDataInicio().isAfter(inicio) && 
                            evento.getDataFim().isBefore(fim))
            .toList();
    }

    @Entao("o usuário deve ver {int} eventos no período")
    public void oUsuarioDeveVerEventosNoPeriodo(int quantidadeEsperada) {
        assertEquals(quantidadeEsperada, eventosFiltrados.size());
    }

    @Quando("o usuário visualiza seus eventos salvos")
    public void oUsuarioVisualizaSeusEventosSalvos() {
        Evento evento = eventos.get(0);
        Inscricao inscricao = new Inscricao(
            InscricaoId.novo(),
            usuarioId,
            evento.getId(),
            LocalDateTime.now(),
            null,
            null
        );
        inscricoes.add(inscricao);
        
        Mockito.when(inscricaoService.listarInscricoesPorUsuario(usuarioId)).thenReturn(inscricoes);
        Mockito.when(eventoService.obter(evento.getId())).thenReturn(evento);
        
        eventosFiltrados = inscricoes.stream()
            .map(i -> eventoService.obter(i.getEventoId()))
            .filter(e -> e != null)
            .toList();
    }

    @Entao("o usuário deve ver o evento {string}")
    public void oUsuarioDeveVerOEvento(String nomeEvento) {
        assertTrue(eventosFiltrados.stream()
            .anyMatch(evento -> evento.getNome().equals(nomeEvento)),
            "Evento " + nomeEvento + " não encontrado na lista de eventos filtrados");
    }

    private Evento criarEvento(Map<String, String> data) {
        return new Evento(
            EventoId.novo(),
            data.get("nome"),
            data.get("descricao"),
            LocalDateTime.parse(data.get("dataInicio")),
            LocalDateTime.parse(data.get("dataFim")),
            data.get("local"),
            Integer.parseInt(data.get("capacidade")),
            data.get("organizador"),
            data.get("categoria"),
            data.get("genero"),
            new BigDecimal(data.get("valor"))
        );
    }
} 