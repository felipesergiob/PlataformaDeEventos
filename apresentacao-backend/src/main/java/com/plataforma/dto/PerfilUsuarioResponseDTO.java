package com.plataforma.dto;

import lombok.Data;
import java.util.List;

@Data
public class PerfilUsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String foto;
    private Integer totalEventosOrganizados;
    private Double mediaAvaliacao;
    private List<EventoResponseDTO> eventosPassados;
    private List<EventoResponseDTO> eventosFuturos;
    private Integer totalSeguidores;
    private Integer totalSeguindo;
} 