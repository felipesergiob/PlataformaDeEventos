package com.plataforma.plataforma.persistencia.jpa.seguidor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SEGUIDOR")
public class SeguidorJpa {
    @Id
    private Integer id;
    @Column(name = "SEGUIDOR_ID")
    private Integer seguidorId;
    @Column(name = "SEGUIDO_ID")
    private Integer seguidoId;
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
} 