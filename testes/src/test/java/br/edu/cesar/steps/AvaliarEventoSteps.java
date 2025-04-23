package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import org.junit.jupiter.api.Assertions;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.usuario.Usuario;

public class AvaliarEventoSteps {
    private Evento evento;
    private Usuario usuario;
    private String mensagemErro;
    private double notaAtual;
    private boolean eventoFinalizado;
    private boolean presencaConfirmada;

    @Dado("que sou um usuário que participou do evento")
    public void queSouUmUsuarioQueParticipouDoEvento() {
        usuario = new Usuario();
        usuario.setNome("João Silva");

        evento = new Evento();
        evento.setTitulo("Workshop de Java");
        evento.adicionarUsuarioConfirmado(usuario);
    }

    @Dado("que sou um usuário que não participou do evento")
    public void queSouUmUsuarioQueNaoParticipouDoEvento() {
        usuario = new Usuario();
        usuario.setNome("João Silva");

        evento = new Evento();
        evento.setTitulo("Workshop de Java");
    }

    @Dado("que sou um usuário que confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEventoFinalizado() {
        usuario = new Usuario();
        usuario.setNome("João Silva");

        evento = new Evento();
        evento.setTitulo("Workshop de Java");
        evento.adicionarUsuarioConfirmado(usuario);
        eventoFinalizado = true;
        presencaConfirmada = true;
    }

    @Dado("que sou um usuário que confirmou presença em um evento")
    public void queSouUmUsuarioQueConfirmouPresencaEmUmEvento() {
        usuario = new Usuario();
        usuario.setNome("João Silva");

        evento = new Evento();
        evento.setTitulo("Workshop de Java");
        evento.adicionarUsuarioConfirmado(usuario);
        eventoFinalizado = false;
        presencaConfirmada = true;
    }

    @Dado("que sou um usuário que não confirmou presença em um evento finalizado")
    public void queSouUmUsuarioQueNaoConfirmouPresencaEmUmEventoFinalizado() {
        usuario = new Usuario();
        usuario.setNome("João Silva");

        evento = new Evento();
        evento.setTitulo("Workshop de Java");
        eventoFinalizado = true;
        presencaConfirmada = false;
    }

    @Quando("avalio o evento com nota {int}")
    public void avalioOEventoComNota(int nota) {
        if (evento.getUsuariosConfirmados().contains(usuario)) {
            evento.setTotalAvaliacoes(evento.getTotalAvaliacoes() + 1);
            double novaMedia = ((evento.getMediaNotas() * (evento.getTotalAvaliacoes() - 1)) + nota) / evento.getTotalAvaliacoes();
            evento.setMediaNotas(novaMedia);
            notaAtual = nota;
        } else {
            mensagemErro = "Apenas participantes podem avaliar o evento";
        }
    }

    @Quando("acesso a página de avaliação do evento")
    public void acessoAPaginaDeAvaliacaoDoEvento() {
        if (!eventoFinalizado) {
            mensagemErro = "O evento ainda não finalizou";
        } else if (!presencaConfirmada) {
            mensagemErro = "Você precisa ter confirmado presença para avaliar o evento";
        }
    }

    @Quando("preencho apenas a nota {int}")
    public void preenchoApenasANota(int nota) {
        if (eventoFinalizado && presencaConfirmada) {
            evento.setTotalAvaliacoes(evento.getTotalAvaliacoes() + 1);
            double novaMedia = ((evento.getMediaNotas() * (evento.getTotalAvaliacoes() - 1)) + nota) / evento.getTotalAvaliacoes();
            evento.setMediaNotas(novaMedia);
            notaAtual = nota;
        }
    }

    @Quando("submeto a avaliação")
    public void submetoAAvaliacao() {}

    @Quando("tento acessar a página de avaliação do evento")
    public void tentoAcessarAPaginaDeAvaliacaoDoEvento() {
        if (!eventoFinalizado) {
            mensagemErro = "O evento ainda não finalizou";
        } else if (!presencaConfirmada) {
            mensagemErro = "Você precisa ter confirmado presença para avaliar o evento";
        }
    }

    @Entao("a avaliação deve ser registrada")
    public void aAvaliacaoDeveSerRegistrada() {
        Assertions.assertEquals(1, evento.getTotalAvaliacoes());
        Assertions.assertEquals(notaAtual, evento.getMediaNotas());
    }

    @Entao("a avaliação deve ser registrada com sucesso")
    public void aAvaliacaoDeveSerRegistradaComSucesso() {
        Assertions.assertEquals(1, evento.getTotalAvaliacoes());
        Assertions.assertEquals(notaAtual, evento.getMediaNotas());
    }

    @Entao("devo ver a mensagem de erro de avaliação {string}")
    public void devoVerAMensagemDeErroDeAvaliacao(String mensagem) {
        Assertions.assertEquals(mensagem, mensagemErro);
    }

    @Entao("a avaliação não deve ser registrada")
    public void aAvaliacaoNaoDeveSerRegistrada() {
        Assertions.assertEquals(0, evento.getTotalAvaliacoes());
        Assertions.assertEquals(0.0, evento.getMediaNotas());
    }

    @Entao("devo ver uma mensagem de confirmação")
    public void devoVerUmaMensagemDeConfirmacao() {
        Assertions.assertTrue(evento.getTotalAvaliacoes() > 0);
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