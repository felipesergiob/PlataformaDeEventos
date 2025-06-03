package com.plataforma.persistencia.jpa.evento;

import com.plataforma.evento.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.persistencia.jpa.usuario.UsuarioImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AvaliacaoImpl {
    private final AvaliacaoJpaRepositorio repositorio;
    private final EventoImpl eventoImpl;
    private final UsuarioImpl usuarioImpl;

    public AvaliacaoImpl(AvaliacaoJpaRepositorio repositorio,
                        EventoImpl eventoImpl,
                        UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.eventoImpl = eventoImpl;
        this.usuarioImpl = usuarioImpl;
    }

    public AvaliacaoJpa toJpa(EventoId eventoId, UsuarioId usuarioId, Integer nota, String comentario) {
        if (eventoId == null || usuarioId == null || nota == null) {
            return null;
        }

        AvaliacaoJpa jpa = new AvaliacaoJpa();
        
        EventoJpa eventoJpa = new EventoJpa();
        eventoJpa.setId(eventoId.getValue());
        jpa.setEvento(eventoJpa);

        UsuarioJpa usuarioJpa = new UsuarioJpa();
        usuarioJpa.setId(usuarioId.getValue());
        jpa.setUsuario(usuarioJpa);

        jpa.setNota(nota);
        jpa.setComentario(comentario);
        jpa.setDataCriacao(LocalDateTime.now());

        return jpa;
    }
} 