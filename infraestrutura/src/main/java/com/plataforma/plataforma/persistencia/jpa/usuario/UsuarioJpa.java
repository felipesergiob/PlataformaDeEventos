package com.plataforma.plataforma.persistencia.jpa.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USUARIO")
public class UsuarioJpa {
    @Id
    private Integer id;

    private String nome;
    private String email;
    private String senha;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "FOTO_PERFIL")
    private String fotoPerfil;

    private Integer seguidores;
} 