package com.plataforma.plataforma.persistencia.jpa.participanteevento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.plataforma.dominio.participanteevento.ParticipanteEvento;
import com.plataforma.dominio.participanteevento.ParticipanteEventoId;
import com.plataforma.dominio.participanteevento.ParticipanteEventoRepositorio;

@Repository
public class ParticipanteEventoRepositorioImpl implements ParticipanteEventoRepositorio {
    @Autowired
    private ParticipanteEventoJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(ParticipanteEvento participanteEvento) {
        var participanteEventoJpa = mapeador.map(participanteEvento, ParticipanteEventoJpa.class);
        repositorio.save(participanteEventoJpa);
    }

    @Override
    public ParticipanteEvento obter(ParticipanteEventoId id) {
        var participanteEventoJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(participanteEventoJpa, ParticipanteEvento.class);
    }
} 