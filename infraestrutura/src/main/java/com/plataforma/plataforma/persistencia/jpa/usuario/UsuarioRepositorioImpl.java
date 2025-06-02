package com.plataforma.plataforma.persistencia.jpa.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plataforma.dominio.usuario.Usuario;
import com.plataforma.dominio.usuario.UsuarioId;
import com.plataforma.dominio.usuario.UsuarioRepositorio;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio {
    @Autowired
    private UsuarioJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(Usuario usuario) {
        var usuarioJpa = mapeador.map(usuario, UsuarioJpa.class);
        repositorio.save(usuarioJpa);
    }

    @Override
    public Usuario obter(UsuarioId id) {
        var usuarioJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(usuarioJpa, Usuario.class);
    }

    @Override
    public Usuario obterPorEmail(String email) {
        var usuarioJpa = repositorio.findByEmail(email);
        return usuarioJpa != null ? mapeador.map(usuarioJpa, Usuario.class) : null;
    }
} 