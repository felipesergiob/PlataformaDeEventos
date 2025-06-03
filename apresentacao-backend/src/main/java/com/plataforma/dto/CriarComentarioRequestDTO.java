package com.plataforma.dto;

import lombok.Data;

@Data
public class CriarComentarioRequestDTO {
    private Long eventoId;
    private String comentario;
    private Long comentarioPaiId;
} 