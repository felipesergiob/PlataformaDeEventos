package com.plataforma.avaliacao;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import java.util.List;

public interface AvaliacaoRepository {
    List<Avaliacao> listarPorEvento(EventoId eventoId);

    int contarParticipantesConfirmados(EventoId eventoId);

    List<Avaliacao> listarNotasEvento(EventoId eventoId);

    void salvar(Avaliacao avaliacao);

    boolean existeInscricaoConfirmada(EventoId eventoId, UsuarioId usuarioId);

    boolean existeAvaliacao(EventoId eventoId, UsuarioId usuarioId);
}