package com.plataforma.persistencia.jpa.usuario;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class UsuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "SENHA", nullable = false, length = 100)
    private String senha;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @Column(name = "FOTO_PERFIL", length = 255)
    private String fotoPerfil;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "SEGUIDORES", nullable = false)
    private Integer seguidores;

    @OneToMany(mappedBy = "seguidor")
    private List<SeguidorJpa> seguindo = new ArrayList<>();

    @OneToMany(mappedBy = "seguido")
    private List<SeguidorJpa> seguidoresList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Integer seguidores) {
        this.seguidores = seguidores;
    }

    public List<SeguidorJpa> getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(List<SeguidorJpa> seguindo) {
        this.seguindo = seguindo;
    }

    public List<SeguidorJpa> getSeguidoresList() {
        return seguidoresList;
    }

    public void setSeguidoresList(List<SeguidorJpa> seguidoresList) {
        this.seguidoresList = seguidoresList;
    }

    @Override
    public String toString() {
        return "UsuarioJpa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
} 