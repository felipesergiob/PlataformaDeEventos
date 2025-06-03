package com.plataforma.persistencia.jpa.usuario;

import com.plataforma.usuario.UsuarioId;
import org.springframework.stereotype.Component;

@Component
public class SeguidorImpl {
    private final SeguidorJpaRepositorio repositorio;
    private final UsuarioImpl usuarioImpl;

    public SeguidorImpl(SeguidorJpaRepositorio repositorio, UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.usuarioImpl = usuarioImpl;
    }

    public SeguidorJpa toJpa(UsuarioId seguidorId, UsuarioId seguidoId) {
        if (seguidorId == null || seguidoId == null) {
            return null;
        }

        SeguidorJpa jpa = new SeguidorJpa();
        UsuarioJpa seguidorJpa = new UsuarioJpa();
        seguidorJpa.setId(seguidorId.getValue());
        jpa.setSeguidor(seguidorJpa);

        UsuarioJpa seguidoJpa = new UsuarioJpa();
        seguidoJpa.setId(seguidoId.getValue());
        jpa.setSeguido(seguidoJpa);

        return jpa;
    }
} 