package com.plataforma.Publicacao;

import java.util.List;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.PublicacaoId;
import com.plataforma.evento.EventoRepository;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class PublicacaoService {
	private final PublicacaoRepository publicacaoRepository;
	private final EventoRepository eventoRepository;

	public PublicacaoService(PublicacaoRepository publicacaoRepository, EventoRepository eventoRepository) {
		notNull(publicacaoRepository, "O repositório de publicações não pode ser nulo");
		notNull(eventoRepository, "O repositório de eventos não pode ser nulo");

		this.publicacaoRepository = publicacaoRepository;
		this.eventoRepository = eventoRepository;
	}

	public void salvar(Publicacao publicacao, UsuarioId criadorId) {
		notNull(publicacao, "A publicação não pode ser nula");
		notNull(criadorId, "O ID do criador não pode ser nulo");
		notNull(publicacao.getEventoId(), "O evento da publicação não pode ser nulo");

		if (isBlank(publicacao.getConteudo())) {
			throw new IllegalArgumentException("O conteúdo da publicação não pode estar vazio");
		}

		if (eventoRepository.obter(publicacao.getEventoId()) == null) {
			throw new IllegalStateException("O evento referenciado não existe");
		}

		Publicacao publicacaoComAutor = new Publicacao(
			publicacao.getId(),
			criadorId,
			publicacao.getEventoId(),
			publicacao.getConteudo()
		);

		publicacao.getFotos().forEach(publicacaoComAutor::adicionarFoto);

		publicacaoRepository.salvar(publicacaoComAutor);
	}

	public Publicacao obter(PublicacaoId id) {
		return publicacaoRepository.obter(id);
	}

	public List<Publicacao> listarPorEvento(EventoId id) {
		return publicacaoRepository.listarPorEvento(id);
	}
}
