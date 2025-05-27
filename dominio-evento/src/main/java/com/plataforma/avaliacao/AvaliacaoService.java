package com.plataforma.avaliacao;

import com.plataforma.compartilhado.EventoId;
import static org.apache.commons.lang3.Validate.notNull;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<Avaliacao> visualizarHistoricoAvaliacoes(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.listarPorEvento(eventoId);
    }

    public double obterMediaAvaliacoes(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.calcularMediaNotas(eventoId);
    }

    public int obterQuantidadeParticipantes(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.contarParticipantesConfirmados(eventoId);
    }

    public Map<String, Object> visualizarResumoAvaliacoes(EventoId eventoId) { //historia 6
        notNull(eventoId, "O id do evento não pode ser nulo");
        
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("avaliacoes", visualizarHistoricoAvaliacoes(eventoId));
        resumo.put("mediaNotas", obterMediaAvaliacoes(eventoId));
        resumo.put("quantidadeParticipantes", obterQuantidadeParticipantes(eventoId));
        
        return resumo;
    }
} 