package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final UsuarioId id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String fotoPerfil;
    private List<UsuarioId> seguidores;

    public Usuario(UsuarioId id, String nome, String email, String senha,
        String telefone) {
             notNull(id, "O id não pode ser nulo");

        this.id = id;
        this.seguidores = new ArrayList<>();

        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTelefone(telefone);
        setFotoPerfil(null);
    }

    public Usuario(UsuarioId id, String nome, String email, String senha,
            String telefone, String fotoPerfil) {
        this(id, nome, email, senha, telefone);
        setFotoPerfil(fotoPerfil);
    }

    public UsuarioId getId() {
        return id;
    }

    public boolean jaSegueUsuario(UsuarioId id) {
        return seguidores.contains(id);
    }

    public List<UsuarioId> getSeguidores() {
        return seguidores;
    }

    public void seguirUsuario(UsuarioId id) {
        if (jaSegueUsuario(id)) {
            throw new IllegalArgumentException("O usuário já está sendo seguido");
        }

        if (this.id.equals(id)) {
            throw new IllegalArgumentException("O usuário não pode seguir a si mesmo");
        }

        seguidores.add(id);
    }

    public void incrementarSeguidores(UsuarioId id) {
        seguidores.add(id);
    }

    public void setNome(String nome) {
        notNull(nome, "O nome não pode ser nulo");
        notBlank(nome, "O nome não pode estar em branco");
        isTrue(nome.length() >= 3, "O nome deve ter pelo menos 3 caracteres");
        isTrue(nome.length() <= 100, "O nome deve ter no máximo 100 caracteres");
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        notNull(email, "O email não pode ser nulo");
        notBlank(email, "O email não pode estar em branco");
        isTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "Email inválido");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        notNull(senha, "A senha não pode ser nula");
        notBlank(senha, "A senha não pode estar em branco");
        isTrue(senha.length() >= 6, "A senha deve ter pelo menos 6 caracteres");
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setTelefone(String telefone) {
        notNull(telefone, "O telefone não pode ser nulo");
        notBlank(telefone, "O telefone não pode estar em branco");
        isTrue(telefone.matches("^\\+?[1-9][0-9]{10,14}$"), "Telefone inválido");
        this.telefone = telefone;
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
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    @Override
    public String toString() {
        return nome;
    }
}
