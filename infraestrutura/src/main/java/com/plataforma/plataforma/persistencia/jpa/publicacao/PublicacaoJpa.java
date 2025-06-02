package com.plataforma.plataforma.persistencia.jpa.publicacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PUBLICACAO")
public class PublicacaoJpa {
    @Id
    private Integer id;
    @Column(name = "EVENTO_ID")
    private Integer eventoId;
    @Column(name = "USUARIO_ID")
    private Integer usuarioId;
    private String conteudo;
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    private String fotos;
} 