package com.plataforma.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

@Entity
public class Usuario {
    
    @Id
    @Column(columnDefinition = "uuid")
    @Getter
    private final UsuarioId id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String fotoPerfil;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
    
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    public enum TipoUsuario {
        PARTICIPANTE,
        ORGANIZADOR
    }

    public Usuario(UsuarioId id, String nome, String email, String senha, 
                  String telefone, TipoUsuario tipo) {
        this(id, nome, email, senha, telefone, tipo, null);
    }

    public Usuario(UsuarioId id, String nome, String email, String senha, 
                  String telefone, TipoUsuario tipo, String fotoPerfil) {
        notNull(id, "O id não pode ser nulo");
        notNull(tipo, "O tipo de usuário não pode ser nulo");
        
        this.id = id;
        this.tipo = tipo;
        
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTelefone(telefone);
        setFotoPerfil(fotoPerfil);
        
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = this.dataCadastro;
        this.ativo = true;
    }

    public void setNome(String nome) {
        notNull(nome, "O nome não pode ser nulo");
        notBlank(nome, "O nome não pode estar em branco");
        isTrue(nome.length() >= 3, "O nome deve ter pelo menos 3 caracteres");
        isTrue(nome.length() <= 100, "O nome deve ter no máximo 100 caracteres");
        this.nome = nome;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        notNull(email, "O email não pode ser nulo");
        notBlank(email, "O email não pode estar em branco");
        isTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "Email inválido");
        this.email = email;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        notNull(senha, "A senha não pode ser nula");
        notBlank(senha, "A senha não pode estar em branco");
        isTrue(senha.length() >= 6, "A senha deve ter pelo menos 6 caracteres");
        this.senha = senha;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getSenha() {
        return senha;
    }

    public void setTelefone(String telefone) {
        notNull(telefone, "O telefone não pode ser nulo");
        notBlank(telefone, "O telefone não pode estar em branco");
        isTrue(telefone.matches("^\\+?[1-9][0-9]{10,14}$"), "Telefone inválido");
        this.telefone = telefone;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setFotoPerfil(String fotoPerfil) {
        if (fotoPerfil != null) {
            notBlank(fotoPerfil, "A URL da foto não pode estar em branco");
            isTrue(fotoPerfil.length() <= 255, "A URL da foto deve ter no máximo 255 caracteres");
            isTrue(fotoPerfil.matches("^https?://.*\\.(jpg|jpeg|png|gif)$"), 
                  "A URL da foto deve ser uma imagem válida (jpg, jpeg, png ou gif)");
        }
        this.fotoPerfil = fotoPerfil;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void tornarOrganizador() {
        this.tipo = TipoUsuario.ORGANIZADOR;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void tornarParticipante() {
        this.tipo = TipoUsuario.PARTICIPANTE;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void ativar() {
        this.ativo = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public boolean ehOrganizador() {
        return tipo == TipoUsuario.ORGANIZADOR;
    }

    public boolean ehParticipante() {
        return tipo == TipoUsuario.PARTICIPANTE;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Foto: %s", 
            nome, 
            tipo, 
            fotoPerfil != null ? "Sim" : "Não");
    }
} 