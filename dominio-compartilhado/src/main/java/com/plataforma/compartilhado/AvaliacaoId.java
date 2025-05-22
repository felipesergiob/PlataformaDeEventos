package com.plataforma.compartilhado;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class AvaliacaoId {
    private final String codigo;

    private AvaliacaoId(String codigo) {
        Validate.notBlank(codigo, "O código não pode ser nulo ou vazio");
        this.codigo = codigo;
    }

    public static AvaliacaoId novo() {
        return new AvaliacaoId(java.util.UUID.randomUUID().toString());
    }

    public static AvaliacaoId de(String codigo) {
        return new AvaliacaoId(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoId avaliacaoId = (AvaliacaoId) o;
        return codigo.equals(avaliacaoId.codigo);
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