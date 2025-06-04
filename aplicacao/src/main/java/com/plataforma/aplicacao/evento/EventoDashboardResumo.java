package com.plataforma.aplicacao.evento;

public interface EventoDashboardResumo {
    Integer getEventoId();
    String getTitulo();
    String getDescricao();
    Integer getTotalConfirmacoes();
    Integer getTotalAvaliacoes();
    Double getMediaNotas();
    Integer getTotalComentarios();
    String getStatus();
} 