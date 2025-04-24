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
    private List<UsuarioId> seguindo;
    private List<UsuarioId> seguidores;
    private boolean isOrganizador;

    public Usuario() {
        this.seguindo = new ArrayList<>();
        this.seguidores = new ArrayList<>();
        this.id = new UsuarioId();
    }

    public Usuario(String nome) {
        this();
        this.nome = nome;
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

    public List<UsuarioId> getSeguindo() {
        return seguindo;
    }

    public List<UsuarioId> getSeguidores() {
        return seguidores;
    }

    public boolean isOrganizador() {
        return isOrganizador;
    }

    public void setOrganizador(boolean isOrganizador) {
        this.isOrganizador = isOrganizador;
    }

    public void seguirUsuario(UsuarioId usuarioId) {
        if (!seguindo.contains(usuarioId)) {
            seguindo.add(usuarioId);
        }
    }

    public void pararSeguir(UsuarioId usuarioId) {
        if (seguindo.contains(usuarioId)) {
            seguindo.remove(usuarioId);
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