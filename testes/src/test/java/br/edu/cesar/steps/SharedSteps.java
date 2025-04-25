package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;

public class SharedSteps extends BaseSteps {
    private TestContext context;

    public SharedSteps() {
        this.context = TestContext.getInstance();
    }

    @Dado("que sou um usuário que participou do evento")
    public void queSouUmUsuarioQueParticipouDoEvento() {
        setupUsuarioQueParticipouDoEvento();
    }

    @Dado("que sou um usuário que não participou do evento")
    public void queSouUmUsuarioQueNaoParticipouDoEvento() {
        setupUsuarioQueNaoParticipouDoEvento();
    }

    @Dado("que o evento está finalizado")
    public void queOEventoEstaFinalizado() {
        setupEventoFinalizado();
    }
} 