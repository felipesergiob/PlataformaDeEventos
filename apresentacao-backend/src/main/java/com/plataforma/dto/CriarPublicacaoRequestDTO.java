package com.plataforma.dto;

import lombok.Data;

@Data
public class CriarPublicacaoRequestDTO {
    private Long eventoId;
    private String conteudo;
    private String fotos;
} 