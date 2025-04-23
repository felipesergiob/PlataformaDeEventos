package br.edu.cesar.steps;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import br.edu.cesar.eventos.dominio.avaliacao.Avaliacao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

public class AvaliarEventoSteps {
    private Evento evento;
    private Avaliacao avaliacao;
    private boolean avaliacaoSucesso;
    private String mensagemErro;

    @Dado("que sou um usuário que confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEventoFinalizado() {
        evento = new Evento();
        evento.setId(new EventoId("123"));
        evento.setStatus("FINALIZADO");
        evento.setUsuarioConfirmado(new UsuarioId("456"));
    }

    @Dado("que sou um usuário que confirmou presença em um evento")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEvento() {
        evento = new Evento();
        evento.setId(new EventoId("123"));
        evento.setStatus("EM_ANDAMENTO");
        evento.setUsuarioConfirmado(new UsuarioId("456"));
    }

    @Dado("que sou um usuário que não confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueNaoConfirmouPresencaEmUmEventoFinalizado() {
        evento = new Evento();
        evento.setId(new EventoId("123"));
        evento.setStatus("FINALIZADO");
        // Usuário não está na lista de confirmados
    }

    @Quando("acesso a página de avaliação do evento")
    public void acessoAPaginaDeAvaliacaoDoEvento() {
        avaliacao = new Avaliacao();
        avaliacao.setEventoId(evento.getId());
        avaliacao.setUsuarioId(new UsuarioId("456"));
    }

    @Quando("preencho a avaliação com nota {int} e comentário {string}")
    public void preenchoAAvaliacaoComNotaEComentario(int nota, String comentario) {
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);
        avaliacaoSucesso = avaliacao.isValid();
    }

    @Quando("preencho apenas a nota {int}")
    public void preenchoApenasANota(int nota) {
        avaliacao.setNota(nota);
        avaliacaoSucesso = avaliacao.isValid();
    }

    @Quando("submeto a avaliação")
    public void submetoAAvaliacao() {
        if (!evento.isFinalizado()) {
            avaliacaoSucesso = false;
            mensagemErro = "O evento ainda não finalizou";
        } else if (!evento.isUsuarioConfirmado(new UsuarioId("456"))) {
            avaliacaoSucesso = false;
            mensagemErro = "Você precisa ter confirmado presença para avaliar o evento";
        }
    }

    @Quando("tento acessar a página de avaliação do evento")
    public void tentoAcessarAPaginaDeAvaliacaoDoEvento() {
        if (!evento.isFinalizado()) {
            mensagemErro = "O evento ainda não finalizou";
        } else if (!evento.isUsuarioConfirmado(new UsuarioId("456"))) {
            mensagemErro = "Você precisa ter confirmado presença para avaliar o evento";
        }
    }

    @Entao("a avaliação deve ser registrada com sucesso")
    public void aAvaliacaoDeveSerRegistradaComSucesso() {
        Assertions.assertTrue(avaliacaoSucesso, "A avaliação deveria ter sido registrada com sucesso");
    }

    @Entao("devo ver uma mensagem de confirmação")
    public void devoVerUmaMensagemDeConfirmacao() {
        Assertions.assertTrue(avaliacaoSucesso, "Deve ver uma mensagem de confirmação");
    }

    @Entao("devo ver uma mensagem informando que o evento ainda não finalizou")
    public void devoVerUmaMensagemInformandoQueOEventoAindaNaoFinalizou() {
        Assertions.assertEquals("O evento ainda não finalizou", mensagemErro);
    }

    @Entao("devo ver uma mensagem informando que preciso ter confirmado presença para avaliar")
    public void devoVerUmaMensagemInformandoQuePrecisoTerConfirmadoPresencaParaAvaliar() {
        Assertions.assertEquals("Você precisa ter confirmado presença para avaliar o evento", mensagemErro);
    }
} 