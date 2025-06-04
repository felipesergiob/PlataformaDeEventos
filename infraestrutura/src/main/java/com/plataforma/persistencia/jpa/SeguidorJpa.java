package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "SEGUIDOR")
@Data
public class SeguidorJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SEGUIDOR_ID", nullable = false)
    private UsuarioJpa seguidor;

    @ManyToOne
    @JoinColumn(name = "SEGUIDO_ID", nullable = false)
    private UsuarioJpa seguido;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
} 