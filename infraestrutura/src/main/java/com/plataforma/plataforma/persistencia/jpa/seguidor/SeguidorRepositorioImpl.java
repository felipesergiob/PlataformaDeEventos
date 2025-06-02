package com.plataforma.plataforma.persistencia.jpa.seguidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.plataforma.dominio.seguidor.Seguidor;
import com.plataforma.dominio.seguidor.SeguidorId;
import com.plataforma.dominio.seguidor.SeguidorRepositorio;

@Repository
public class SeguidorRepositorioImpl implements SeguidorRepositorio {
    @Autowired
    private SeguidorJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(Seguidor seguidor) {
        var seguidorJpa = mapeador.map(seguidor, SeguidorJpa.class);
        repositorio.save(seguidorJpa);
    }

    @Override
    public Seguidor obter(SeguidorId id) {
        var seguidorJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(seguidorJpa, Seguidor.class);
    }
} 