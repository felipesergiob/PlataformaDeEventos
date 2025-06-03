package com.plataforma.dto;

import lombok.Data;

@Data
public class CriarAvaliacaoRequestDTO {
    private Long eventoId;
    private Integer nota;
    private String comentario;
} 