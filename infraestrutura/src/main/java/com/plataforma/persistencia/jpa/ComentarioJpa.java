package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMENTARIO")
@Data
public class ComentarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COMENTARIO", nullable = false)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMENTARIO_PAI_ID")
    private ComentarioJpa comentarioPai;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;
} 