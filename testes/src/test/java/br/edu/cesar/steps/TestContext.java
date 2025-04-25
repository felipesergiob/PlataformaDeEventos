package br.edu.cesar.steps;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.usuario.Usuario;

public class TestContext {
    private static TestContext instance;
    private Usuario usuario;
    private Evento evento;
    private boolean presencaConfirmada;
    private boolean eventoFinalizado;

    public TestContext() {
        this.presencaConfirmada = false;
        this.eventoFinalizado = false;
    }

    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public boolean isPresencaConfirmada() {
        return presencaConfirmada;
    }

    public void setPresencaConfirmada(boolean presencaConfirmada) {
        this.presencaConfirmada = presencaConfirmada;
    }

    public boolean isEventoFinalizado() {
        return eventoFinalizado;
    }

    public void setEventoFinalizado(boolean eventoFinalizado) {
        this.eventoFinalizado = eventoFinalizado;
    }

    public void reset() {
        this.evento = null;
        this.usuario = null;
        this.presencaConfirmada = false;
        this.eventoFinalizado = false;
    }
} 