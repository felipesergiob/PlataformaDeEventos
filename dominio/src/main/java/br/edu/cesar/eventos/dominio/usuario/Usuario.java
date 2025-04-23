package br.edu.cesar.eventos.dominio.usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private UsuarioId id;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> seguindo;
    private List<Usuario> seguidores;
    private boolean isOrganizador;

    public Usuario() {
        this.seguindo = new ArrayList<>();
        this.seguidores = new ArrayList<>();
        this.id = new UsuarioId();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UsuarioId getId() {
        return id;
    }

    public void setId(UsuarioId id) {
        this.id = id;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Usuario> getSeguindo() {
        return seguindo;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public boolean isOrganizador() {
        return isOrganizador;
    }

    public void setOrganizador(boolean isOrganizador) {
        this.isOrganizador = isOrganizador;
    }

    public void seguirUsuario(Usuario usuario) {
        if (!seguindo.contains(usuario)) {
            seguindo.add(usuario);
            usuario.seguidores.add(this);
        }
    }

    public void pararSeguir(Usuario usuario) {
        if (seguindo.contains(usuario)) {
            seguindo.remove(usuario);
            usuario.seguidores.remove(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 