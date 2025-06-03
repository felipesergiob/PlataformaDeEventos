package com.plataforma.persistencia.jpa.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.persistencia.jpa.usuario.UsuarioImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class EventoImpl {
    private final EventoJpaRepositorio repositorio;
    private final UsuarioImpl usuarioImpl;

    public EventoImpl(EventoJpaRepositorio repositorio, UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.usuarioImpl = usuarioImpl;
    }

    public EventoJpa toJpa(Evento evento) {
        if (evento == null) {
            return null;
        }

        EventoJpa jpa = new EventoJpa();
        jpa.setId(evento.getId().getValue());
        jpa.setTitulo(evento.getNome());
        jpa.setDescricao(evento.getDescricao());
        jpa.setDataInicio(evento.getDataInicio());
        jpa.setDataFim(evento.getDataFim());
        jpa.setLocal(evento.getLocal());
        jpa.setGenero(evento.getGenero());
        jpa.setValor(evento.getValor());
        jpa.setParticipantes(evento.getInscritos());
        jpa.setDataCriacao(LocalDateTime.now());

        UsuarioJpa organizadorJpa = new UsuarioJpa();
        organizadorJpa.setId(evento.getOrganizador().getValue());
        jpa.setOrganizador(organizadorJpa);

        return jpa;
    }

    public Evento toDomain(EventoJpa jpa) {
        if (jpa == null) {
            return null;
        }

        return new Evento(
            new EventoId(jpa.getId()),
            jpa.getTitulo(),
            jpa.getDescricao(),
            jpa.getDataInicio(),
            jpa.getDataFim(),
            jpa.getLocal(),
            new UsuarioId(jpa.getOrganizador().getId()),
            jpa.getGenero(),
            jpa.getValor()
        );
    }
} 