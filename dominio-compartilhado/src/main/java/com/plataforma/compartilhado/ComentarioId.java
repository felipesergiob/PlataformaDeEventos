package com.plataforma.compartilhado;

import java.util.UUID;

public class ComentarioId {
    private final String id;

    private ComentarioId(String id) {
        this.id = id;
    }

    public static ComentarioId de(String id) {
        return new ComentarioId(id);
    }

    public static ComentarioId novo() {
        return new ComentarioId(UUID.randomUUID().toString());
    }

    public String getValor() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComentarioId that = (ComentarioId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }
} 