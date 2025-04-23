package br.edu.cesar.eventos.dominio.usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private UsuarioId id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> seguindo;
    private List<Usuario> seguidores;

    public Usuario() {
        this.seguindo = new ArrayList<>();
        this.seguidores = new ArrayList<>();
    }

    public UsuarioId getId() {
        return id;
    }

    public void setId(UsuarioId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getSeguindo() {
        return seguindo;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
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
} 