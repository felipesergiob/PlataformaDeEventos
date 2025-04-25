package br.edu.cesar.steps;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;

public class BaseSteps {
    protected TestContext context;
    protected boolean registroSucesso;

    public BaseSteps() {
        this.context = TestContext.getInstance();
    }

    protected void setupUsuarioQueParticipouDoEvento() {
        Usuario usuario = new Usuario();
        usuario.setId(new UsuarioId("1"));
        usuario.setNome("João Silva");
        
        Evento evento = new Evento();
        evento.setId(new EventoId("1"));
        evento.setTitulo("Workshop de Java");
        evento.adicionarUsuarioConfirmado(usuario.getId());
        
        context.setUsuario(usuario);
        context.setEvento(evento);
        context.setPresencaConfirmada(true);
    }

    protected void setupUsuarioQueNaoParticipouDoEvento() {
        Usuario usuario = new Usuario();
        usuario.setId(new UsuarioId("1"));
        usuario.setNome("João Silva");
        
        Evento evento = new Evento();
        evento.setId(new EventoId("1"));
        evento.setTitulo("Workshop de Java");
        
        context.setUsuario(usuario);
        context.setEvento(evento);
        context.setPresencaConfirmada(false);
    }

    protected void setupEventoFinalizado() {
        context.getEvento().setStatus("FINALIZADO");
        context.setEventoFinalizado(true);
    }
} 