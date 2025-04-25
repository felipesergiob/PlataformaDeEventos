package br.edu.cesar.eventos.dominio.evento;

import java.util.UUID;

public class EventoId {
    private String id;

    public EventoId() {
        this.id = UUID.randomUUID().toString();
    }

    public EventoId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoId eventoId = (EventoId) o;
        return id.equals(eventoId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}