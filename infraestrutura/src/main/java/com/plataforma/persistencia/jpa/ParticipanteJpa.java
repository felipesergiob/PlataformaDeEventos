package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARTICIPANTE_EVENTO")
@Data
public class ParticipanteJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
} 