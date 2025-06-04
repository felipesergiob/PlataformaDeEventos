package com.plataforma.aplicacao.publicacao;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PublicacaoResumoImpl implements PublicacaoResumo {
    private String id;
    private String conteudo;
    private String fotos;
    private LocalDateTime dataCriacao;
    private String usuarioId;
    private String eventoId;
} 