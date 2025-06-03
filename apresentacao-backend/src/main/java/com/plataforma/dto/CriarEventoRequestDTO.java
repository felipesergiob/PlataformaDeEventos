package com.plataforma.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CriarEventoRequestDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private String genero;
    private BigDecimal valor;
    private String imagem;
    private Integer participantes;
} 