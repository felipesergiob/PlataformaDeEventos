package br.edu.cesar.eventos.dominio.evento;

import lombok.Data;
import java.util.UUID;

@Data
public class EventoId {
    private final UUID id;

    public EventoId() {
        this.id = UUID.randomUUID();
    }

    public EventoId(UUID id) {
        this.id = id;
    }
} 