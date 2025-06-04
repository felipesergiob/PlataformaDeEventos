package com.plataforma.aplicacao.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarAvaliacaoRequest {
    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
    
    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;
    
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    private Integer nota;
    
    @Size(min = 10, message = "O comentário deve ter no mínimo 10 caracteres")
    private String comentario;
} 