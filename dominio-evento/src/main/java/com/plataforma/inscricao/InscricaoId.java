package com.plataforma.inscricao;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class InscricaoId {
    private final String codigo;

    private InscricaoId(String codigo) {
        Validate.notBlank(codigo, "O código não pode ser nulo ou vazio");
        this.codigo = codigo;
    }

    public static InscricaoId novo() {
        return new InscricaoId(java.util.UUID.randomUUID().toString());
    }

    public static InscricaoId de(String codigo) {
        return new InscricaoId(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscricaoId inscricaoId = (InscricaoId) o;
        return codigo.equals(inscricaoId.codigo);
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