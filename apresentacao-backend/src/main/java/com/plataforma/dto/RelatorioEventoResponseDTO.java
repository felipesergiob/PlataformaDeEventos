package com.plataforma.dto;

import lombok.Data;
import java.util.List;

@Data
public class RelatorioEventoResponseDTO {
    private Long eventoId;
    private String titulo;
    private Integer totalConfirmacoes;
    private Integer totalAvaliacoes;
    private Double mediaNota;
    private Integer totalComentarios;
    private List<AvaliacaoResponseDTO> avaliacoes;
    private List<ComentarioResponseDTO> comentarios;
} 