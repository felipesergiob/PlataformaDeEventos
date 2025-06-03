package com.plataforma.plataforma.persistencia.jpa.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventoRepositorioImpl implements EventoRepository {
    private final EventoJpaRepository jpaRepository;

    public EventoRepositorioImpl(EventoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Evento evento) {
        EventoJpa eventoJpa = new EventoJpa();
        eventoJpa.setId(evento.getId().getValor());
        eventoJpa.setTitulo(evento.getTitulo());
        eventoJpa.setDescricao(evento.getDescricao());
        eventoJpa.setDataInicio(evento.getDataInicio());
        eventoJpa.setDataFim(evento.getDataFim());
        eventoJpa.setLocalId(evento.getLocalId().getValor());
        eventoJpa.setOrganizadorId(evento.getOrganizadorId().getValor());
        eventoJpa.setDataCriacao(evento.getDataCriacao());
        eventoJpa.setAtivo(evento.isAtivo());
        
        jpaRepository.save(eventoJpa);
    }

    @Override
    public Evento obter(EventoId id) {
        return jpaRepository.findById(id.getValor())
            .map(this::toEvento)
            .orElse(null);
    }

    @Override
    public List<Evento> listarPorOrganizador(UsuarioId organizadorId) {
        return jpaRepository.findByOrganizadorId(organizadorId.getValor())
            .stream()
            .map(this::toEvento)
            .collect(Collectors.toList());
    }

    private Evento toEvento(EventoJpa jpa) {
        return new Evento(
            new EventoId(jpa.getId()),
            jpa.getTitulo(),
            jpa.getDescricao(),
            jpa.getDataInicio(),
            jpa.getDataFim(),
            new UsuarioId(jpa.getLocalId()),
            new UsuarioId(jpa.getOrganizadorId()),
            jpa.getDataCriacao(),
            jpa.isAtivo()
        );
    }
} 