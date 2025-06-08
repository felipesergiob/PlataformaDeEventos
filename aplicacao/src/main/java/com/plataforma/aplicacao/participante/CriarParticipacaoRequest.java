package com.plataforma.aplicacao.participante;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CriarParticipacaoRequest {
    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;

    @Pattern(regexp = "^(CONFIRMADO|SALVO)$", message = "Status deve ser CONFIRMADO ou SALVO")
    private String status = "SALVO"; // valor padrão
} 