package com.plataforma.aplicacao.publicacao;

import java.time.LocalDateTime;

public interface PublicacaoResumo {
    String getId();
    String getTitulo();
    String getConteudo();
    String getFotos();
    LocalDateTime getDataCriacao();
    String getUsuarioId();
    String getEventoId();
} 