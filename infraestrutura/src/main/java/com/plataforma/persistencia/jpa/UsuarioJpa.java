package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO")
@Data
public class UsuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String telefone;

    @Column(name = "FOTO_PERFIL")
    private String fotoPerfil;

    @Column(nullable = false)
    private Integer seguidores = 0;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Override
    public String toString() {
        return nome;
    }
}