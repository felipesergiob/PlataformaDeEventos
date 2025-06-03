package com.plataforma.plataforma.persistencia.jpa.participanteevento;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "PARTICIPANTE_EVENTO")
public class ParticipanteEventoJpa {
    @Id
    @Column(name = "ID", columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "EVENTO_ID", columnDefinition = "uuid")
    private UUID eventoId;
    
    @Column(name = "USUARIO_ID", columnDefinition = "uuid")
    private UUID usuarioId;
    
    @Column(name = "DATA_INSCRICAO")
    private LocalDateTime dataInscricao;
    
    private boolean ativo;
} 