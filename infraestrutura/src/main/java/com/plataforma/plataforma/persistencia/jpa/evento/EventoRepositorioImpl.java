package com.plataforma.plataforma.persistencia.jpa.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.plataforma.dominio.evento.Evento;
import com.plataforma.dominio.evento.EventoId;
import com.plataforma.dominio.evento.EventoRepositorio;

@Repository
public class EventoRepositorioImpl implements EventoRepositorio {
    @Autowired
    private EventoJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(Evento evento) {
        var eventoJpa = mapeador.map(evento, EventoJpa.class);
        repositorio.save(eventoJpa);
    }

    @Override
    public Evento obter(EventoId id) {
        var eventoJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(eventoJpa, Evento.class);
    }
} 