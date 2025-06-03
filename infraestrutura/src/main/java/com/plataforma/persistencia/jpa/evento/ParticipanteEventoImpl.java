package com.plataforma.persistencia.jpa.evento;

import com.plataforma.evento.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.persistencia.jpa.usuario.UsuarioImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ParticipanteEventoImpl {
    private final ParticipanteEventoJpaRepositorio repositorio;
    private final EventoImpl eventoImpl;
    private final UsuarioImpl usuarioImpl;

    public ParticipanteEventoImpl(ParticipanteEventoJpaRepositorio repositorio, 
                                EventoImpl eventoImpl,
                                UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.eventoImpl = eventoImpl;
        this.usuarioImpl = usuarioImpl;
    }

    public ParticipanteEventoJpa toJpa(EventoId eventoId, UsuarioId usuarioId, String status) {
        if (eventoId == null || usuarioId == null || status == null) {
            return null;
        }

        ParticipanteEventoJpa jpa = new ParticipanteEventoJpa();
        
        EventoJpa eventoJpa = new EventoJpa();
        eventoJpa.setId(eventoId.getValue());
        jpa.setEvento(eventoJpa);

        UsuarioJpa usuarioJpa = new UsuarioJpa();
        usuarioJpa.setId(usuarioId.getValue());
        jpa.setUsuario(usuarioJpa);

        jpa.setStatus(status);
        jpa.setDataCriacao(LocalDateTime.now());

        return jpa;
    }
} 