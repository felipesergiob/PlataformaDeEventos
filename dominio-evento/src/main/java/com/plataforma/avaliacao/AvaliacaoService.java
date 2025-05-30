package com.plataforma.avaliacao;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import static org.apache.commons.lang3.Validate.notNull;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final EventoRepository eventoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, EventoRepository eventoRepository) {
        notNull(avaliacaoRepository, "O repositório de avaliações não pode ser nulo");
        notNull(eventoRepository, "O repositório de eventos não pode ser nulo");
        this.avaliacaoRepository = avaliacaoRepository;
        this.eventoRepository = eventoRepository;
    }

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        notNull(avaliacaoRepository, "O repositório de avaliações não pode ser nulo");
        this.avaliacaoRepository = avaliacaoRepository;
        this.eventoRepository = null;
    }

    public List<Avaliacao> visualizarHistoricoAvaliacoes(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.listarPorEvento(eventoId);
    }

    public double obterMediaAvaliacoes(EventoId eventoId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.listarNotasEvento(eventoId);
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        double media = avaliacoes.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .getAsDouble();
        return media;
    }

    public int obterQuantidadeParticipantes(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.contarParticipantesConfirmados(eventoId);
    }

    public Map<String, Object> visualizarResumoAvaliacoes(EventoId eventoId) { // historia 6
        notNull(eventoId, "O id do evento não pode ser nulo");

        Map<String, Object> resumo = new HashMap<>();
        resumo.put("avaliacoes", visualizarHistoricoAvaliacoes(eventoId));
        resumo.put("mediaNotas", obterMediaAvaliacoes(eventoId));
        resumo.put("quantidadeParticipantes", obterQuantidadeParticipantes(eventoId));

        return resumo;
    }

    public void avaliarEvento(EventoId eventoId, Avaliacao avaliacao, UsuarioId usuarioId) { // historia 5
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(avaliacao, "A avaliação não pode ser nula");
        notNull(usuarioId, "O id do usuário não pode ser nulo");

        if (!avaliacaoRepository.existeInscricaoConfirmada(eventoId, usuarioId)) {
            throw new IllegalArgumentException("Usuário não confirmou presença neste evento");
        }

        Evento evento = eventoRepository.obter(eventoId);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        if (!evento.jaOcorreu()) {
            throw new IllegalArgumentException("Avaliação só pode ser feita após o término do evento");
        }

        if (avaliacaoRepository.existeAvaliacao(eventoId, usuarioId)) {
            throw new IllegalArgumentException("Usuário já avaliou este evento");
        }

        avaliacaoRepository.salvar(avaliacao);
    }
}