package br.edu.cesar.steps;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import br.edu.cesar.eventos.dominio.avaliacao.Avaliacao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VisualizarHistoricoEventosSteps {
    private List<Evento> eventos;
    private Evento eventoSelecionado;
    private List<Avaliacao> avaliacoes;

    @Dado("que sou um usuário da plataforma")
    public void queSouUmUsuarioDaPlataforma() {
        // Simula um usuário logado
    }

    @Dado("existe um organizador com eventos passados")
    public void existeUmOrganizadorComEventosPassados() {
        eventos = new ArrayList<>();

        Evento showRock = new Evento();
        showRock.setId(new EventoId("1"));
        showRock.setTitulo("Show de Rock");
        showRock.setData("01/01/2023");
        showRock.setTotalConfirmacoes(100);
        showRock.setTotalAvaliacoes(80);
        showRock.setMediaNotas(4.5);
        showRock.setTotalComentarios(25);
        eventos.add(showRock);

        Evento festivalJazz = new Evento();
        festivalJazz.setId(new EventoId("2"));
        festivalJazz.setTitulo("Festival de Jazz");
        festivalJazz.setData("15/02/2023");
        festivalJazz.setTotalConfirmacoes(150);
        festivalJazz.setTotalAvaliacoes(120);
        festivalJazz.setMediaNotas(4.8);
        festivalJazz.setTotalComentarios(30);
        eventos.add(festivalJazz);

        avaliacoes = new ArrayList<>();
        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setUsuarioId(new UsuarioId("joao"));
        avaliacao1.setNota(5);
        avaliacao1.setComentario("Show incrível, organização impecável!");
        avaliacoes.add(avaliacao1);

        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setUsuarioId(new UsuarioId("maria"));
        avaliacao2.setNota(4);
        avaliacao2.setComentario("Adorei o evento, superou minhas expectativas!");
        avaliacoes.add(avaliacao2);
    }

    @Dado("existe um organizador com eventos passados sem avaliações")
    public void existeUmOrganizadorComEventosPassadosSemAvaliacoes() {
        eventos = new ArrayList<>();

        Evento showRock = new Evento();
        showRock.setId(new EventoId("1"));
        showRock.setTitulo("Show de Rock");
        showRock.setData("01/01/2023");
        showRock.setTotalConfirmacoes(100);
        showRock.setTotalAvaliacoes(0);
        showRock.setMediaNotas(0.0);
        showRock.setTotalComentarios(0);
        eventos.add(showRock);

        Evento festivalJazz = new Evento();
        festivalJazz.setId(new EventoId("2"));
        festivalJazz.setTitulo("Festival de Jazz");
        festivalJazz.setData("15/02/2023");
        festivalJazz.setTotalConfirmacoes(150);
        festivalJazz.setTotalAvaliacoes(0);
        festivalJazz.setMediaNotas(0.0);
        festivalJazz.setTotalComentarios(0);
        eventos.add(festivalJazz);
    }

    @Quando("acesso o perfil do organizador")
    public void acessoOPerfilDoOrganizador() {
        // Simula o acesso ao perfil
    }

    @Quando("clico em um evento específico")
    public void clicoEmUmEventoEspecifico() {
        eventoSelecionado = eventos.get(0);
    }

    @Entao("devo ver a lista de eventos passados com:")
    public void devoVerAListaDeEventosPassadosCom(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> eventosEsperados = dataTable.asMaps();

        for (int i = 0; i < eventosEsperados.size(); i++) {
            Map<String, String> eventoEsperado = eventosEsperados.get(i);
            Evento eventoAtual = eventos.get(i);

            Assertions.assertEquals(eventoEsperado.get("Evento"), eventoAtual.getTitulo());
            Assertions.assertEquals(eventoEsperado.get("Data"), eventoAtual.getData());
            Assertions.assertEquals(Double.parseDouble(eventoEsperado.get("Média")), eventoAtual.getMediaNotas());
            Assertions.assertEquals(Integer.parseInt(eventoEsperado.get("Participantes")), eventoAtual.getTotalConfirmacoes());
            Assertions.assertEquals(Integer.parseInt(eventoEsperado.get("Comentários")), eventoAtual.getTotalComentarios());
        }
    }

    @Entao("devo ver os detalhes do evento no histórico:")
    public void devoVerOsDetalhesDoEventoNoHistorico(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> detalhes = dataTable.asMap();

        Assertions.assertEquals(detalhes.get("Nome"), eventoSelecionado.getTitulo());
        Assertions.assertEquals(detalhes.get("Data"), eventoSelecionado.getData());
        Assertions.assertEquals(Double.parseDouble(detalhes.get("Média de Notas")), eventoSelecionado.getMediaNotas());
        Assertions.assertEquals(Integer.parseInt(detalhes.get("Total de Avaliações")), eventoSelecionado.getTotalAvaliacoes());
        Assertions.assertEquals(Integer.parseInt(detalhes.get("Total de Comentários")), eventoSelecionado.getTotalComentarios());
        Assertions.assertEquals(Integer.parseInt(detalhes.get("Participantes Confirmados")), eventoSelecionado.getTotalConfirmacoes());

        double taxaEngajamento = (double) eventoSelecionado.getTotalAvaliacoes() / eventoSelecionado.getTotalConfirmacoes() * 100;
        Assertions.assertEquals(Double.parseDouble(detalhes.get("Taxa de Engajamento").replace("%", "")), taxaEngajamento);
    }

    @Entao("devo ver os comentários dos participantes:")
    public void devoVerOsComentariosDosParticipantes(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> comentariosEsperados = dataTable.asMaps();

        for (int i = 0; i < comentariosEsperados.size(); i++) {
            Map<String, String> comentarioEsperado = comentariosEsperados.get(i);
            Avaliacao avaliacao = avaliacoes.get(i);

            Assertions.assertEquals(comentarioEsperado.get("Usuário"), avaliacao.getUsuarioId().getId());
            Assertions.assertEquals(comentarioEsperado.get("Comentário"), avaliacao.getComentario());
        }
    }
}