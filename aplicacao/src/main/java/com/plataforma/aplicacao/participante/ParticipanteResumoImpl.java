package com.plataforma.aplicacao.participante;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ParticipanteResumoImpl implements ParticipanteResumo {
    private String id;
    private String eventoId;
    private String usuarioId;
    private String status;
    private LocalDateTime dataCriacao;
} 