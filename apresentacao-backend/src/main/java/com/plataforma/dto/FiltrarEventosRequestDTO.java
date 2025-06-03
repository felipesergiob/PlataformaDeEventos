package com.plataforma.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FiltrarEventosRequestDTO {
    private String genero;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private BigDecimal valorMaximo;
    private String periodoDia; // MANHA, TARDE, NOITE
} 