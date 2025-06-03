package com.plataforma.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private String genero;
    private BigDecimal valor;
    private String imagem;
    private Integer participantes;
    private Long organizadorId;
    private String nomeOrganizador;
    private Double mediaAvaliacao;
    private Integer totalAvaliacoes;
} 