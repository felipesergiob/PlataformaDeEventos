package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import br.edu.cesar.eventos.dominio.interacao.Avaliacao;
import org.junit.jupiter.api.Assertions;

public class AvaliarEventoSteps extends BaseSteps {
    private Avaliacao avaliacao;
    private String mensagemErro;

    @Dado("que sou um usuário que confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEventoFinalizado() {
        setupUsuarioQueParticipouDoEvento();
        setupEventoFinalizado();
    }

    @Dado("que sou um usuário que confirmou presença em um evento")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEvento() {
        setupUsuarioQueParticipouDoEvento();
        // Ensure the event is not finalized
        context.getEvento().setStatus("ABERTO");
        context.setEventoFinalizado(false);
    }

    @Dado("que sou um usuário que não confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueNaoConfirmouPresencaEmUmEventoFinalizado() {
        setupUsuarioQueNaoParticipouDoEvento();
        setupEventoFinalizado();
    }

    @Quando("acesso a página de avaliação do evento")
    public void acessoAPaginaDeAvaliacaoDoEvento() {
        // Simula o acesso à página
    }

    @Quando("tento acessar a página de avaliação do evento")
    public void tentoAcessarAPaginaDeAvaliacaoDoEvento() {
        if (!context.isPresencaConfirmada()) {
            mensagemErro = "Você precisa ter confirmado presença para avaliar o evento";
        } else if (!context.isEventoFinalizado()) {
            mensagemErro = "O evento ainda não finalizou";
        }
    }

    @Quando("preencho apenas a nota {int}")
    public void preenchoApenasANota(Integer nota) {
        avaliacao = new Avaliacao();
        avaliacao.setNota(nota);
        avaliacao.setEventoId(context.getEvento().getId());
        avaliacao.setUsuarioId(context.getUsuario().getId());
    }

    @Quando("submeto a avaliação")
    public void submetoAAvaliacao() {
        try {
            avaliacao.validar();
            registroSucesso = true;
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            registroSucesso = false;
        }
    }

    @Quando("avalio o evento com nota {int}")
    public void avalioOEventoComNota(Integer nota) {
        avaliacao = new Avaliacao();
        avaliacao.setNota(nota);
        avaliacao.setEventoId(context.getEvento().getId());
        avaliacao.setUsuarioId(context.getUsuario().getId());
        
        try {
            if (!context.isPresencaConfirmada()) {
                mensagemErro = "Apenas participantes podem avaliar o evento";
                registroSucesso = false;
            } else if (!context.isEventoFinalizado()) {
                mensagemErro = "O evento ainda não finalizou";
                registroSucesso = false;
            } else {
                avaliacao.validar();
                registroSucesso = true;
            }
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            registroSucesso = false;
        }
    }

    @Entao("a avaliação deve ser registrada com sucesso")
    public void aAvaliacaoDeveSerRegistradaComSucesso() {
        Assertions.assertTrue(registroSucesso);
        Assertions.assertNotNull(avaliacao);
        Assertions.assertNotNull(avaliacao.getDataAvaliacao());
    }

    @Entao("a avaliação deve ser registrada")
    public void aAvaliaçãoDeveSerRegistrada() {
        Assertions.assertTrue(registroSucesso);
        Assertions.assertNotNull(avaliacao);
    }

    @Entao("devo ver uma mensagem de confirmação")
    public void devoVerUmaMensagemDeConfirmacao() {
        Assertions.assertTrue(registroSucesso);
    }

    @Entao("devo ver uma mensagem informando que o evento ainda não finalizou")
    public void devoVerUmaMensagemInformandoQueOEventoAindaNaoFinalizou() {
        Assertions.assertEquals("O evento ainda não finalizou", mensagemErro);
    }

    @Entao("devo ver uma mensagem informando que preciso ter confirmado presença para avaliar")
    public void devoVerUmaMensagemInformandoQuePrecisoTerConfirmadoPresencaParaAvaliar() {
        Assertions.assertEquals("Você precisa ter confirmado presença para avaliar o evento", mensagemErro);
    }

    @Entao("devo ver a mensagem de erro de avaliação {string}")
    public void devoVerAMensagemDeErroDeAvaliacao(String mensagem) {
        Assertions.assertEquals(mensagem, mensagemErro);
    }

    @Entao("a avaliação não deve ser registrada")
    public void aAvaliacaoNaoDeveSerRegistrada() {
        Assertions.assertFalse(registroSucesso);
    }
}