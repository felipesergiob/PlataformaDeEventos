package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "AVALIACAO")
@Data
public class AvaliacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "NOTA", nullable = false)
    private Integer nota;

    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
} 