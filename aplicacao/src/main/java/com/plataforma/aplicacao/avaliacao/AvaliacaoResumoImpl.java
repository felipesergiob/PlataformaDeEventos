package com.plataforma.aplicacao.avaliacao;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AvaliacaoResumoImpl implements AvaliacaoResumo {
    private String id;
    private String eventoId;
    private String usuarioId;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataCriacao;
} 