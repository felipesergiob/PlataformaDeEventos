package com.plataforma.comentario;

import java.util.List;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.ComentarioId;

public interface ComentarioRepository {
    void salvar(Comentario comentario);

    Comentario obter(ComentarioId id);

    List<Comentario> listarPorEvento(EventoId eventoId);

    List<Comentario> listarPorUsuario(UsuarioId usuarioId);

    List<Comentario> listarRespostas(ComentarioId comentarioId);

    void remover(ComentarioId id);

    boolean existe(ComentarioId id);

    boolean ehOrganizadorDoEvento(UsuarioId usuarioId, EventoId eventoId);
}
