package com.plataforma.historico;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import java.util.List;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;

public class HistoricoService {
    private final HistoricoRepository historicoRepository;

    public HistoricoService(HistoricoRepository historicoRepository) {
        notNull(historicoRepository, "O repositório de histórico não pode ser nulo");
        this.historicoRepository = historicoRepository;
    }

    public void registrarParticipacao(Historico historico) {
        notNull(historico, "O histórico não pode ser nulo");
        historicoRepository.salvar(historico);
    }

    public Historico obter(HistoricoId id) {
        notNull(id, "O ID do histórico não pode ser nulo");
        Historico historico = historicoRepository.obter(id);
        if (historico == null) {
            throw new IllegalArgumentException("Histórico não encontrado");
        }
        return historico;
    }

    public List<Historico> listarPorUsuario(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return historicoRepository.listarPorUsuario(usuarioId);
    }

    public List<Historico> listarPorEvento(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return historicoRepository.listarPorEvento(eventoId);
    }

    public List<Historico> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        notNull(inicio, "A data de início não pode ser nula");
        notNull(fim, "A data de fim não pode ser nula");
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }
        return historicoRepository.listarPorPeriodo(inicio, fim);
    }

    public List<Historico> listarComAvaliacao() {
        return historicoRepository.listarComAvaliacao();
    }

    public List<Historico> listarSemAvaliacao() {
        return historicoRepository.listarSemAvaliacao();
    }

    public void registrarAvaliacao(HistoricoId id, AvaliacaoId avaliacaoId, Integer nota, String comentario) {
        notNull(id, "O ID do histórico não pode ser nulo");
        notNull(avaliacaoId, "O ID da avaliação não pode ser nulo");
        notNull(nota, "A nota não pode ser nula");
        notNull(comentario, "O comentário não pode ser nulo");

        Historico historico = obter(id);
        if (historico.temAvaliacao()) {
            throw new IllegalStateException("Este histórico já possui uma avaliação");
        }

        Historico historicoAtualizado = new Historico(
            historico.getId(),
            historico.getEventoId(),
            historico.getNomeEvento(),
            historico.getDataEvento(),
            historico.getDataParticipacao(),
            avaliacaoId,
            nota,
            comentario
        );

        historicoRepository.salvar(historicoAtualizado);
    }

    public void excluir(HistoricoId id) {
        notNull(id, "O ID do histórico não pode ser nulo");
        if (!historicoRepository.existe(id)) {
            throw new IllegalArgumentException("Histórico não encontrado");
        }
        historicoRepository.excluir(id);
    }

    public double obterMediaNotas(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        List<Historico> historicos = listarPorUsuario(usuarioId);
        return historicos.stream()
            .filter(Historico::temAvaliacao)
            .mapToInt(Historico::getNota)
            .average()
            .orElse(0.0);
    }

    public List<Historico> listarEventosRecentes(UsuarioId usuarioId, int quantidade) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        return listarPorUsuario(usuarioId).stream()
            .sorted((h1, h2) -> h2.getDataParticipacao().compareTo(h1.getDataParticipacao()))
            .limit(quantidade)
            .toList();
    }
} 