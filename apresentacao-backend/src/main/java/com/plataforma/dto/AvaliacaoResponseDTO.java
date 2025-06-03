package com.plataforma.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AvaliacaoResponseDTO {
    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private String nomeUsuario;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataCriacao;
    private List<ComentarioResponseDTO> respostas;
} 