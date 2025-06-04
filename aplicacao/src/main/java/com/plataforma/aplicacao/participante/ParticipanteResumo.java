package com.plataforma.aplicacao.participante;

import java.time.LocalDateTime;

public interface ParticipanteResumo {
    String getId();
    String getEventoId();
    String getUsuarioId();
    String getStatus();
    LocalDateTime getDataCriacao();
} 