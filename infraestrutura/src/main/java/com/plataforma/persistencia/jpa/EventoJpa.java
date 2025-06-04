package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "EVENTO")
@Data
public class EventoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descricao;

    @Column(name = "DATA_INICIO", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "DATA_FIM", nullable = false)
    private LocalDateTime dataFim;

    @Column(nullable = false)
    private String local;

    @Column
    private String genero;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private String imagem;

    @Column(nullable = false)
    private Integer participantes = 0;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZADOR_ID", nullable = false)
    private UsuarioJpa organizador;
} 