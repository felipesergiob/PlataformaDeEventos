package com.plataforma.plataforma.persistencia.jpa.evento;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EVENTO")
public class EventoJpa {
    @Id
    private Integer id;
    private String titulo;
    private String descricao;
    @Column(name = "DATA_INICIO")
    private LocalDateTime dataInicio;
    @Column(name = "DATA_FIM")
    private LocalDateTime dataFim;
    private String local;
    private String genero;
    private BigDecimal valor;
    private String imagem;
    private Integer participantes;
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    @Column(name = "ORGANIZADOR_ID")
    private Integer organizadorId;
} 