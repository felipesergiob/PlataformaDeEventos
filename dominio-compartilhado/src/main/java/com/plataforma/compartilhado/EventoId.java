package com.plataforma.compartilhado;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class EventoId {
    private final String codigo;

    private EventoId(String codigo) {
        Validate.notBlank(codigo, "O código não pode ser nulo ou vazio");
        this.codigo = codigo;
    }

    public static EventoId novo() {
        return new EventoId(java.util.UUID.randomUUID().toString());
    }

    public static EventoId de(String codigo) {
        return new EventoId(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoId eventoId = (EventoId) o;
        return codigo.equals(eventoId.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return codigo;
    }
} 