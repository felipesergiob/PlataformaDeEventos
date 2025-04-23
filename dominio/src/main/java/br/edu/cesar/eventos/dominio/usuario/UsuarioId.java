package br.edu.cesar.eventos.dominio.usuario;

public class UsuarioId {
    private String id;

    public UsuarioId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) o;
        return id.equals(usuarioId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 