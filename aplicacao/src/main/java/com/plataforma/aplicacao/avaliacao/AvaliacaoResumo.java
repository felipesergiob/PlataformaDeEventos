package com.plataforma.aplicacao.avaliacao;

import java.time.LocalDateTime;

public interface AvaliacaoResumo {
    String getId();
    String getEventoId();
    String getUsuarioId();
    Integer getNota();
    String getComentario();
    LocalDateTime getDataCriacao();
} 