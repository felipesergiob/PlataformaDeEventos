package com.plataforma.plataforma.persistencia.jpa.comentario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMENTARIO")
public class ComentarioJpa {
    @Id
    private Integer id;
    @Column(name = "EVENTO_ID")
    private Integer eventoId;
    @Column(name = "USUARIO_ID")
    private Integer usuarioId;
    private String comentario;
    @Column(name = "COMENTARIO_PAI_ID")
    private Integer comentarioPaiId;
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
} 