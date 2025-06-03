package com.plataforma.persistencia.jpa.usuario;

import com.plataforma.usuario.Usuario;
import com.plataforma.usuario.UsuarioId;
import org.springframework.stereotype.Component;

@Component
public class UsuarioImpl {
    private final UsuarioJpaRepositorio repositorio;

    public UsuarioImpl(UsuarioJpaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public UsuarioJpa toJpa(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioJpa jpa = new UsuarioJpa();
        jpa.setId(usuario.getId().getValue());
        jpa.setNome(usuario.getNome());
        jpa.setEmail(usuario.getEmail());
        jpa.setSenha(usuario.getSenha());
        jpa.setTelefone(usuario.getTelefone());
        jpa.setFotoPerfil(usuario.getFotoPerfil());
        jpa.setSeguidores(usuario.getSeguidores().size());
        return jpa;
    }

    public Usuario toDomain(UsuarioJpa jpa) {
        if (jpa == null) {
            return null;
        }

        return new Usuario(
            new UsuarioId(jpa.getId()),
            jpa.getNome(),
            jpa.getEmail(),
            jpa.getSenha(),
            jpa.getTelefone(),
            jpa.getFotoPerfil()
        );
    }
} 