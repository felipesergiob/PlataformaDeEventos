package com.plataforma.persistencia.jpa.evento;

import com.plataforma.evento.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.persistencia.jpa.usuario.UsuarioImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ComentarioImpl {
    private final ComentarioJpaRepositorio repositorio;
    private final EventoImpl eventoImpl;
    private final UsuarioImpl usuarioImpl;

    public ComentarioImpl(ComentarioJpaRepositorio repositorio,
                         EventoImpl eventoImpl,
                         UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.eventoImpl = eventoImpl;
        this.usuarioImpl = usuarioImpl;
    }

    public ComentarioJpa toJpa(EventoId eventoId, UsuarioId usuarioId, String comentario, Long comentarioPaiId) {
        if (eventoId == null || usuarioId == null || comentario == null) {
            return null;
        }

        ComentarioJpa jpa = new ComentarioJpa();
        
        EventoJpa eventoJpa = new EventoJpa();
        eventoJpa.setId(eventoId.getValue());
        jpa.setEvento(eventoJpa);

        UsuarioJpa usuarioJpa = new UsuarioJpa();
        usuarioJpa.setId(usuarioId.getValue());
        jpa.setUsuario(usuarioJpa);

        jpa.setComentario(comentario);
        
        if (comentarioPaiId != null) {
            ComentarioJpa comentarioPai = new ComentarioJpa();
            comentarioPai.setId(comentarioPaiId);
            jpa.setComentarioPai(comentarioPai);
        }

        jpa.setDataCriacao(LocalDateTime.now());

        return jpa;
    }
} 