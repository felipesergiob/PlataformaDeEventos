package com.plataforma.plataforma.persistencia.jpa.seguidor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "SEGUIDOR")
public class SeguidorJpa {
    @Id
    @Column(name = "ID", columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "SEGUIDOR_ID", columnDefinition = "uuid")
    private UUID seguidorId;
    
    @Column(name = "SEGUIDO_ID", columnDefinition = "uuid")
    private UUID seguidoId;
    
    @Column(name = "DATA_SEGUIMENTO")
    private LocalDateTime dataSeguimento;
    
    private boolean ativo;
} 