package com.plataforma.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PublicacaoResponseDTO {
    private Long id;
    private Long eventoId;
    private String tituloEvento;
    private Long usuarioId;
    private String nomeUsuario;
    private String conteudo;
    private String fotos;
    private LocalDateTime dataCriacao;
} 