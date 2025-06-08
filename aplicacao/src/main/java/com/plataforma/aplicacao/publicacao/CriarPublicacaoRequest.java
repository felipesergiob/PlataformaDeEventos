package com.plataforma.aplicacao.publicacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarPublicacaoRequest {
    @NotBlank(message = "O título da publicação é obrigatório")
    private String titulo;

    @NotBlank(message = "O conteúdo da publicação é obrigatório")
    private String conteudo;
    
    private String fotos;
    
    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
    
    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;
} 