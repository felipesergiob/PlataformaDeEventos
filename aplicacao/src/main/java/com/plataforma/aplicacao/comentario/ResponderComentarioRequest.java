package com.plataforma.aplicacao.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResponderComentarioRequest {
    @NotNull(message = "O ID do comentário a ser respondido é obrigatório")
    private Integer comentarioPaiId;
    
    @NotBlank(message = "O texto da resposta é obrigatório")
    private String comentario;
    
    @NotNull(message = "O ID do organizador é obrigatório")
    private Integer usuarioId;
    
    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
} 