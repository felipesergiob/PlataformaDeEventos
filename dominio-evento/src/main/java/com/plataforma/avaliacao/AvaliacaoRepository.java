package com.plataforma.avaliacao;

import com.plataforma.compartilhado.EventoId;
import java.util.List;

public interface AvaliacaoRepository {
    List<Avaliacao> listarPorEvento(EventoId eventoId);

    double calcularMediaNotas(EventoId eventoId);

    int contarParticipantesConfirmados(EventoId eventoId);
} 