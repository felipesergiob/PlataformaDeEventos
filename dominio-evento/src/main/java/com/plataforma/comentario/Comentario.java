package com.plataforma.comentario;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.ComentarioId;

import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class Comentario {
	private final ComentarioId id;
	private String texto;
	private LocalDateTime dataCriacao;
	private final EventoId eventoId;
	private final UsuarioId autorId;
	private ComentarioId comentarioPaiId;

	public Comentario(ComentarioId id, String texto, EventoId eventoId, UsuarioId autorId) {
		notNull(id, "O id não pode ser nulo");
		notNull(eventoId, "O id do evento não pode ser nulo");
		notNull(autorId, "O id do autor não pode ser nulo");

		this.id = id;
		this.eventoId = eventoId;
		this.autorId = autorId;
		this.dataCriacao = LocalDateTime.now();

		setTexto(texto);
	}

	public ComentarioId getId() {
		return id;
	}

	public void setTexto(String texto) {
		notNull(texto, "O texto não pode ser nulo");
		notBlank(texto, "O texto não pode estar em branco");
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public EventoId getEventoId() {
		return eventoId;
	}

	public UsuarioId getAutorId() {
		return autorId;
	}

	public ComentarioId getComentarioPaiId() {
		return comentarioPaiId;
	}

	public void setComentarioPaiId(ComentarioId comentarioPaiId) {
		notNull(comentarioPaiId, "O id do comentário pai não pode ser nulo");
		this.comentarioPaiId = comentarioPaiId;
	}

	public boolean isResposta() {
		return comentarioPaiId != null;
	}

	@Override
	public String toString() {
		return String.format("Comentário de %s em %s", autorId, dataCriacao);
	}
}
