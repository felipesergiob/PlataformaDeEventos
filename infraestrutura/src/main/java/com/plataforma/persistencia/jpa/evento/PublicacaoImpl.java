package com.plataforma.persistencia.jpa.evento;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.persistencia.jpa.usuario.UsuarioImpl;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PublicacaoImpl {
    private final PublicacaoJpaRepositorio repositorio;
    private final EventoImpl eventoImpl;
    private final UsuarioImpl usuarioImpl;

    public PublicacaoImpl(PublicacaoJpaRepositorio repositorio,
                         EventoImpl eventoImpl,
                         UsuarioImpl usuarioImpl) {
        this.repositorio = repositorio;
        this.eventoImpl = eventoImpl;
        this.usuarioImpl = usuarioImpl;
    }

    public PublicacaoJpa toJpa(EventoId eventoId, UsuarioId usuarioId, String conteudo, String fotos) {
        if (eventoId == null || usuarioId == null || conteudo == null) {
            return null;
        }

        PublicacaoJpa jpa = new PublicacaoJpa();
        
        EventoJpa eventoJpa = new EventoJpa();
        eventoJpa.setId(eventoId.getValor());
        jpa.setEvento(eventoJpa);

        UsuarioJpa usuarioJpa = new UsuarioJpa();
        usuarioJpa.setId(usuarioId.getValor());
        jpa.setUsuario(usuarioJpa);

        jpa.setConteudo(conteudo);
        jpa.setFotos(fotos);
        jpa.setDataCriacao(LocalDateTime.now());

        return jpa;
    }
} 