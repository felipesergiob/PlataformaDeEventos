package br.edu.cesar.eventos.dominio.avaliacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;

public class Avaliacao {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private int nota;
    private String comentario;

    public EventoId getEventoId() {
        return eventoId;
    }

    public void setEventoId(EventoId eventoId) {
        this.eventoId = eventoId;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioId usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isValid() {
        return eventoId != null && 
               usuarioId != null && 
               nota >= 1 && 
               nota <= 5;
    }
} 