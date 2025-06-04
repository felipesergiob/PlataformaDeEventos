package com.plataforma.compartilhado;

import java.util.UUID;

public class PublicacaoId {
    private final UUID valor;

    public PublicacaoId(UUID valor) {
        this.valor = valor;
    }

    public static PublicacaoId gerar() {
        return new PublicacaoId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicacaoId that = (PublicacaoId) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
} 