package com.plataforma.comentario;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.ComentarioId;
import java.util.List;
import static org.apache.commons.lang3.Validate.notNull;

public class ComentarioService {
	private final ComentarioRepository comentarioRepository;

	public ComentarioService(ComentarioRepository comentarioRepository) {
		notNull(comentarioRepository, "O repositório de comentários não pode ser nulo");
		this.comentarioRepository = comentarioRepository;
	}

	public Comentario adicionarComentario(String texto, EventoId eventoId, UsuarioId autorId) {
		notNull(texto, "O texto do comentário não pode ser nulo");
		notNull(eventoId, "O ID do evento não pode ser nulo");
		notNull(autorId, "O ID do autor não pode ser nulo");

		Comentario comentario = new Comentario(ComentarioId.novo(), texto, eventoId, autorId);
		comentarioRepository.salvar(comentario);
		return comentario;
	}

	public Comentario responderComentario(String texto, ComentarioId comentarioOriginalId, UsuarioId autorId) {
		notNull(texto, "O texto da resposta não pode ser nulo");
		notNull(comentarioOriginalId, "O ID do comentário original não pode ser nulo");
		notNull(autorId, "O ID do autor não pode ser nulo");

		if (!comentarioRepository.existe(comentarioOriginalId)) {
			throw new IllegalArgumentException("Comentário original não encontrado");
		}

		Comentario comentarioOriginal = comentarioRepository.obter(comentarioOriginalId);

		if (!comentarioRepository.ehOrganizadorDoEvento(autorId, comentarioOriginal.getEventoId())) {
			throw new IllegalArgumentException("Apenas o organizador do evento pode responder aos comentários");
		}

		Comentario resposta = new Comentario(ComentarioId.novo(), texto, comentarioOriginal.getEventoId(), autorId);
		resposta.setComentarioPaiId(comentarioOriginalId);

		comentarioRepository.salvar(resposta);
		return resposta;
	}

	public void editarComentario(ComentarioId comentarioId, String novoTexto, UsuarioId autorId) {
		notNull(comentarioId, "O ID do comentário não pode ser nulo");
		notNull(novoTexto, "O novo texto não pode ser nulo");
		notNull(autorId, "O ID do autor não pode ser nulo");

		Comentario comentario = comentarioRepository.obter(comentarioId);
		if (comentario == null) {
			throw new IllegalArgumentException("Comentário não encontrado");
		}

		if (!comentario.getAutorId().equals(autorId) &&
				!comentarioRepository.ehOrganizadorDoEvento(autorId, comentario.getEventoId())) {
			throw new IllegalArgumentException(
					"Apenas o autor do comentário ou o organizador do evento podem editá-lo");
		}

		comentario.setTexto(novoTexto);
		comentarioRepository.salvar(comentario);
	}

	public void removerComentario(ComentarioId comentarioId, UsuarioId autorId) {
		notNull(comentarioId, "O ID do comentário não pode ser nulo");
		notNull(autorId, "O ID do autor não pode ser nulo");

		Comentario comentario = comentarioRepository.obter(comentarioId);
		if (comentario == null) {
			throw new IllegalArgumentException("Comentário não encontrado");
		}

		if (!comentario.getAutorId().equals(autorId) &&
				!comentarioRepository.ehOrganizadorDoEvento(autorId, comentario.getEventoId())) {
			throw new IllegalArgumentException(
					"Apenas o autor do comentário ou o organizador do evento podem removê-lo");
		}

		comentarioRepository.remover(comentarioId);
	}

	public List<Comentario> listarComentariosDoEvento(EventoId eventoId) {
		notNull(eventoId, "O ID do evento não pode ser nulo");
		return comentarioRepository.listarPorEvento(eventoId);
	}

	public List<Comentario> listarRespostas(ComentarioId comentarioId) {
		notNull(comentarioId, "O ID do comentário não pode ser nulo");
		return comentarioRepository.listarRespostas(comentarioId);
	}
}
