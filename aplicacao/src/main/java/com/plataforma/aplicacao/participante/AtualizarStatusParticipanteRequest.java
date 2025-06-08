package com.plataforma.aplicacao.participante;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AtualizarStatusParticipanteRequest {
    @NotNull(message = "O status é obrigatório")
    @Pattern(regexp = "^(CONFIRMADO|SALVO)$", message = "Status deve ser CONFIRMADO ou SALVO")
    private String status;
} 