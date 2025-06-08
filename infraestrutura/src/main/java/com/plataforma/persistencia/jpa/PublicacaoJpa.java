package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "PUBLICACAO")
@Data
public class PublicacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "CONTEUDO", nullable = false)
    private String conteudo;

    @Column(name = "FOTOS")
    private String fotos;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;
} 