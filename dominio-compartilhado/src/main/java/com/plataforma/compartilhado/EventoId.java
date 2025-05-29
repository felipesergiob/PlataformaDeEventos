package com.plataforma.compartilhado;

import lombok.Getter;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

@Getter
public class EventoId {
    private final int id;

    private EventoId(int id) {
        Validate.notNull(id, "O código não pode ser nulo ou vazio");
        Validate.isTrue(id > 0, "O código não pode ser menor que 1");

        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof EventoId) {
            EventoId eventoId = (EventoId) obj;

            return id == eventoId.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    public static EventoId de(int id) {
        return new EventoId(id);
    }
}