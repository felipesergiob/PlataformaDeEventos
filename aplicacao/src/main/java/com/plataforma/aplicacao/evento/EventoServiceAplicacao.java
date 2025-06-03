package com.plataforma.aplicacao.evento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class EventoServiceAplicacao {
    private final EventoRepositorioAplicacao eventoRepositorio;
    private final AvaliacaoRepositorioAplicacao avaliacaoRepositorio;
    private final PublicacaoRepositorioAplicacao publicacaoRepositorio;

    public EventoServiceAplicacao(
            EventoRepositorioAplicacao eventoRepositorio,
            AvaliacaoRepositorioAplicacao avaliacaoRepositorio,
            PublicacaoRepositorioAplicacao publicacaoRepositorio) {
        notNull(eventoRepositorio, "O repositório de eventos não pode ser nulo");
        notNull(avaliacaoRepositorio, "O repositório de avaliações não pode ser nulo");
        notNull(publicacaoRepositorio, "O repositório de publicações não pode ser nulo");

        this.eventoRepositorio = eventoRepositorio;
        this.avaliacaoRepositorio = avaliacaoRepositorio;
        this.publicacaoRepositorio = publicacaoRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Evento> filtrarEventos(String genero, LocalDateTime data, BigDecimal preco) {
        return eventoRepositorio.filtrarEventos(genero, data, preco);
    }

    @Transactional(readOnly = true)
    public List<Evento> listarEventosCalendario(String usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return eventoRepositorio.listarEventosCalendario(usuarioId);
    }

    @Transactional(readOnly = true)
    public PerfilOrganizador visualizarPerfilOrganizador(String organizadorId) {
        notNull(organizadorId, "O ID do organizador não pode ser nulo");
        return eventoRepositorio.obterPerfilOrganizador(organizadorId);
    }

    @Transactional(readOnly = true)
    public DashboardEvento visualizarDashboard(String eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return eventoRepositorio.obterDashboard(eventoId);
    }

    @Transactional
    public void avaliarEvento(String eventoId, Avaliacao avaliacao) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        notNull(avaliacao, "A avaliação não pode ser nula");
        avaliacaoRepositorio.salvar(avaliacao);
    }

    @Transactional(readOnly = true)
    public List<Avaliacao> listarAvaliacoes(String eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return avaliacaoRepositorio.listarPorEvento(eventoId);
    }

    @Transactional
    public void seguirUsuario(String seguidorId, String seguidoId) {
        notNull(seguidorId, "O ID do seguidor não pode ser nulo");
        notNull(seguidoId, "O ID do seguido não pode ser nulo");
        eventoRepositorio.salvarSeguidor(seguidorId, seguidoId);
    }

    @Transactional
    public void responderAvaliacao(String eventoId, String avaliacaoId, String resposta) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        notNull(avaliacaoId, "O ID da avaliação não pode ser nulo");
        notNull(resposta, "A resposta não pode ser nula");
        avaliacaoRepositorio.salvarResposta(eventoId, avaliacaoId, resposta);
    }

    @Transactional
    public void criarPublicacao(String eventoId, Publicacao publicacao) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        notNull(publicacao, "A publicação não pode ser nula");
        publicacaoRepositorio.salvar(publicacao);
    }

    @Transactional(readOnly = true)
    public List<Evento> listarEventosDestaque() {
        return eventoRepositorio.listarEventosDestaque();
    }
} 