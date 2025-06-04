package com.plataforma.aplicacao.comentario;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ComentarioResumoImpl implements ComentarioResumo {
    private String id;
    private String comentario;
    private String comentarioPaiId;
    private LocalDateTime dataCriacao;
    private String usuarioId;
    private String eventoId;
} 