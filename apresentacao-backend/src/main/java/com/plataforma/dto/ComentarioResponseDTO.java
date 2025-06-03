package com.plataforma.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComentarioResponseDTO {
    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private String nomeUsuario;
    private String comentario;
    private LocalDateTime dataCriacao;
    private Long comentarioPaiId;
    private List<ComentarioResponseDTO> respostas;
} 