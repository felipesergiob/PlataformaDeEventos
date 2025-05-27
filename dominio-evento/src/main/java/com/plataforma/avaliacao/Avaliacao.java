package com.plataforma.avaliacao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import java.time.LocalDateTime;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notNull;

public class Avaliacao {

    private final AvaliacaoId id;
    private final UsuarioId usuarioId;
    private final EventoId eventoId;
    private final Integer nota;
    private final String comentario;
    private final LocalDateTime dataAvaliacao;
    private final Boolean ativo;

    public Avaliacao(AvaliacaoId id, UsuarioId usuarioId, EventoId eventoId,
            Integer nota, String comentario, LocalDateTime dataAvaliacao,
            Boolean ativo) {
        notNull(id, "O id não pode ser nulo");
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(dataAvaliacao, "A data da avaliação não pode ser nula");
        notNull(nota, "A nota não pode ser nula");

        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.nota = nota;
        this.dataAvaliacao = dataAvaliacao;
        this.comentario = requireNonNull(comentario, "Comentário não pode ser nulo");
        this.ativo = ativo;
    }

    public AvaliacaoId getId() {
        return id;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public Integer getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean estaAtiva() {
        return isAtivo();
    }

    @Override
    public String toString() {
        return String.format("Avaliação [ID: %s, Evento: %s, Usuário: %s, Nota: %d, Data: %s]",
                id, eventoId, usuarioId, nota, dataAvaliacao);
    }
}