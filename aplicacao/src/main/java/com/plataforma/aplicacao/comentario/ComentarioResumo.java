package com.plataforma.aplicacao.comentario;

import java.time.LocalDateTime;

public interface ComentarioResumo {
    String getId();
    String getComentario();
    String getComentarioPaiId();
    LocalDateTime getDataCriacao();
    String getUsuarioId();
    String getEventoId();
} 