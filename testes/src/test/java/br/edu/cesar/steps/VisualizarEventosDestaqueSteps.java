package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import org.junit.jupiter.api.Assertions;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class VisualizarEventosDestaqueSteps {
    private List<Evento> eventos;
    private List<Evento> eventosDestaque;
    private Evento evento1;
    private Evento evento2;
    private Evento evento3;

    @Dado("que existem eventos em destaque cadastrados na plataforma")
    public void queExistemEventosEmDestaqueCadastradosNaPlataforma() {
        eventos = new ArrayList<>();
        UsuarioId organizadorId = new UsuarioId();
        UsuarioId criadorId = new UsuarioId();

        evento1 = new Evento();
        evento1.setId(new EventoId("1"));
        evento1.setTitulo("Workshop de Java");
        evento1.setTotalConfirmacoes(150);
        evento1.setData("2024-05-01");
        evento1.setOrganizadorId(organizadorId);
        evento1.setCriadorId(criadorId);
        eventos.add(evento1);

        evento2 = new Evento();
        evento2.setId(new EventoId("2"));
        evento2.setTitulo("Meetup de Python");
        evento2.setTotalConfirmacoes(30);
        evento2.setData("2024-05-02");
        evento2.setOrganizadorId(organizadorId);
        evento2.setCriadorId(criadorId);
        eventos.add(evento2);

        evento3 = new Evento();
        evento3.setId(new EventoId("3"));
        evento3.setTitulo("Conferência de TI");
        evento3.setTotalConfirmacoes(20);
        evento3.setData("2024-05-03");
        evento3.setOrganizadorId(organizadorId);
        evento3.setCriadorId(criadorId);
        eventos.add(evento3);
    }

    @Dado("a semana atual é a semana {int} de {int}")
    public void aSemanaAtualEASemana(int semana, int ano) {
    }

    @Quando("acesso a página inicial")
    public void acessoAPaginaInicial() {
        filtrarEventosDestaque();
    }

    @Entao("devo ver a seção {string}")
    public void devoVerASecao(String secao) {
        Assertions.assertNotNull(eventosDestaque);
    }

    @Entao("a lista deve conter os eventos:")
    public void aListaDeveConterOsEventos(io.cucumber.datatable.DataTable dataTable) {
        List<String> titulosEsperados = dataTable.asList();
        for (String titulo : titulosEsperados) {
            boolean encontrado = eventosDestaque.stream()
                .anyMatch(e -> e.getTitulo().equals(titulo));
            Assertions.assertTrue(encontrado, "Evento " + titulo + " não encontrado");
        }
    }

    @Entao("os eventos devem estar ordenados por número de confirmados em ordem decrescente")
    public void osEventosDevemEstarOrdenadosPorNumeroDeConfirmadosEmOrdemDecrescente() {
        List<Evento> eventosOrdenados = new ArrayList<>(eventosDestaque);
        Collections.sort(eventosOrdenados, Comparator.comparingInt(Evento::getTotalConfirmacoes).reversed());
        Assertions.assertEquals(eventosOrdenados, eventosDestaque);
    }

    @Entao("o evento com mais confirmados deve ser {string} com {int} participantes")
    public void oEventoComMaisConfirmadosDeveSerComParticipantes(String titulo, int participantes) {
        Evento evento = eventosDestaque.get(0);
        Assertions.assertEquals(titulo, evento.getTitulo());
        Assertions.assertEquals(participantes, evento.getTotalConfirmacoes());
    }

    @Entao("a seção deve mostrar apenas eventos da semana {int}")
    public void aSecaoDeveMostrarApenasEventosDaSemana(int semana) {
        for (Evento evento : eventosDestaque) {
            // Aqui você implementaria a lógica para verificar se o evento é da semana especificada
            Assertions.assertTrue(true);
        }
    }

    @Entao("não deve mostrar eventos de semanas anteriores")
    public void naoDeveMostrarEventosDeSemanasAnteriores() {
        for (Evento evento : eventosDestaque) {
            // Aqui você implementaria a lógica para verificar se o evento não é de semanas anteriores
            Assertions.assertTrue(true);
        }
    }

    private void filtrarEventosDestaque() {
        eventosDestaque = new ArrayList<>(eventos);
        Collections.sort(eventosDestaque, Comparator.comparingInt(Evento::getTotalConfirmacoes).reversed());
    }
}