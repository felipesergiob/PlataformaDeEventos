package com.plataforma.plataforma.persistencia.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class UsuarioJpa {
    @Id
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String dataCriacao;
} 