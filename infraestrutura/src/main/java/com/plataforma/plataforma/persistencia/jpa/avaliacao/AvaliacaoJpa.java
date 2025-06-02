package com.plataforma.plataforma.persistencia.jpa.avaliacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AVALIACAO")
public class AvaliacaoJpa {
    @Id
    private Integer id;
    @Column(name = "EVENTO_ID")
    private Integer eventoId;
    @Column(name = "USUARIO_ID")
    private Integer usuarioId;
    private Integer nota;
    private String comentario;
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
} 