package com.plataforma.aplicacao.evento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventoResumoImpl implements EventoResumo {
    private String id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private String genero;
    private BigDecimal valor;
    private String imagem;
    private Integer participantes;
    private LocalDateTime dataCriacao;
    private String organizadorId;
} 