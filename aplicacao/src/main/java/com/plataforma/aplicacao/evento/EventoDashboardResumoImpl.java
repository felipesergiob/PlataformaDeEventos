package com.plataforma.aplicacao.evento;

import lombok.Data;

@Data
public class EventoDashboardResumoImpl implements EventoDashboardResumo {
    private Integer eventoId;
    private String titulo;
    private String descricao;
    private Integer totalConfirmacoes;
    private Integer totalAvaliacoes;
    private Double mediaNotas;
    private Integer totalComentarios;
    private String status;
} 