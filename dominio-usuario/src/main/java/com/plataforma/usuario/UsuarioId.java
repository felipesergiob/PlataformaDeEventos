package com.plataforma.usuario;

import java.util.UUID;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class UsuarioId {
    private final String codigo;

    public UsuarioId(String codigo) {
        notNull(codigo, "O código não pode ser nulo");
        notBlank(codigo, "O código não pode estar em branco");
        
        if (!testarCodigo(codigo)) {
            throw new IllegalArgumentException("Código inválido: " + codigo);
        }
        this.codigo = codigo;
    }

    private boolean testarCodigo(String codigo) {
        try {
            UUID.fromString(codigo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public static UsuarioId novo() {
        return new UsuarioId(UUID.randomUUID().toString());
    }

    public static UsuarioId de(String valor) {
        notNull(valor, "O valor não pode ser nulo");
        notBlank(valor, "O valor não pode estar em branco");
        return new UsuarioId(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) obj;
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