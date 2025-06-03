package com.plataforma.plataforma.persistencia.jpa.evento;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "EVENTO")
public class EventoJpa {
    @Id
    @Column(name = "ID", columnDefinition = "uuid")
    private UUID id;
    
    private String titulo;
    private String descricao;
    
    @Column(name = "DATA_INICIO")
    private LocalDateTime dataInicio;
    
    @Column(name = "DATA_FIM")
    private LocalDateTime dataFim;
    
    @Column(name = "LOCAL_ID")
    private UUID localId;
    
    @Column(name = "ORGANIZADOR_ID")
    private UUID organizadorId;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    private boolean ativo;
    
    private String local;
    private String genero;
    private BigDecimal valor;
    private String imagem;
    private Integer participantes;
} 