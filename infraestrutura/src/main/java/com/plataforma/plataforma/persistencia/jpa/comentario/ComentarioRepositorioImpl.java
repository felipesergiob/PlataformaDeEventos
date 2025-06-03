package com.plataforma.plataforma.persistencia.jpa.comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.plataforma.dominio.comentario.Comentario;
import com.plataforma.compartilhado.ComentarioId;
import com.plataforma.dominio.comentario.ComentarioRepositorio;

@Repository
public class ComentarioRepositorioImpl implements ComentarioRepositorio {
    @Autowired
    private ComentarioJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(Comentario comentario) {
        var comentarioJpa = mapeador.map(comentario, ComentarioJpa.class);
        repositorio.save(comentarioJpa);
    }

    @Override
    public Comentario obter(ComentarioId id) {
        var comentarioJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(comentarioJpa, Comentario.class);
    }
} 