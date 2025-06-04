package com.plataforma.aplicacao.evento;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventoFiltro {
    private String genero;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Double precoMinimo;
    private Double precoMaximo;
    private String periodoHorario; // MANHA, TARDE, NOITE
    private Boolean gratuito;
} 