package br.edu.cesar.eventos.dominio.usuario;

import java.util.UUID;

public class UsuarioId {
    private final String id;

    public UsuarioId() {
        this.id = UUID.randomUUID().toString();
    }

    public UsuarioId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) obj;
        return id.equals(usuarioId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}