package br.edu.cesar.eventos.dominio.usuario;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioId {
    private final UUID id;

    public UsuarioId() {
        this.id = UUID.randomUUID();
    }

    public UsuarioId(UUID id) {
        this.id = id;
    }
} 