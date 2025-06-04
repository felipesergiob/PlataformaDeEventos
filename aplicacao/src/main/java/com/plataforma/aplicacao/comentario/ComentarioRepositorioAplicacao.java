package com.plataforma.aplicacao.comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepositorioAplicacao {
    void salvar(ComentarioResumo comentario);
    Optional<ComentarioResumo> buscarPorId(Integer id);
    List<ComentarioResumo> listarPorEvento(Integer eventoId);
    List<ComentarioResumo> listarPorUsuario(Integer usuarioId);
    List<ComentarioResumo> listarRespostas(Integer comentarioPaiId);
} 