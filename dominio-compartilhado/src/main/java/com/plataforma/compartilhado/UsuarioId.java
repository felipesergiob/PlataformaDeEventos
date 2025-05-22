package com.plataforma.compartilhado;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class UsuarioId {
    private final String codigo;

    private UsuarioId(String codigo) {
        Validate.notBlank(codigo, "O código não pode ser nulo ou vazio");
        this.codigo = codigo;
    }

    public static UsuarioId novo() {
        return new UsuarioId(java.util.UUID.randomUUID().toString());
    }

    public static UsuarioId de(String codigo) {
        return new UsuarioId(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) o;
        return codigo.equals(usuarioId.codigo);
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