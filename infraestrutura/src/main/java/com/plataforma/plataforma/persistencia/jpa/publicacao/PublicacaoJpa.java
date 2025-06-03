package com.plataforma.plataforma.persistencia.jpa.publicacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "PUBLICACAO")
public class PublicacaoJpa {
    @Id
    @Column(name = "ID", columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "EVENTO_ID", columnDefinition = "uuid")
    private UUID eventoId;
    
    @Column(name = "USUARIO_ID", columnDefinition = "uuid")
    private UUID usuarioId;
    
    private String conteudo;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    private String fotos;
} 