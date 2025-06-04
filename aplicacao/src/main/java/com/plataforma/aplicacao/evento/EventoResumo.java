package com.plataforma.aplicacao.evento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface EventoResumo {
    String getId();
    String getTitulo();
    String getDescricao();
    LocalDateTime getDataInicio();
    LocalDateTime getDataFim();
    String getLocal();
    String getGenero();
    BigDecimal getValor();
    String getImagem();
    Integer getParticipantes();
    LocalDateTime getDataCriacao();
    String getOrganizadorId();
} 