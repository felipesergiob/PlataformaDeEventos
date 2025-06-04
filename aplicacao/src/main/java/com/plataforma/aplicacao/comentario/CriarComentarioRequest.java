package com.plataforma.aplicacao.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarComentarioRequest {
    @NotBlank(message = "O texto do comentário é obrigatório")
    private String comentario;
    
    private Integer comentarioPaiId;
    
    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
    
    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;
} 