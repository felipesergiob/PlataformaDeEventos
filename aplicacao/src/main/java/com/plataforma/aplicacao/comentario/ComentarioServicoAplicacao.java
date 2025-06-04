package com.plataforma.aplicacao.comentario;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioServicoAplicacao {
    
    private final ComentarioRepositorioAplicacao comentarioRepositorio;

    public void criarComentario(CriarComentarioRequest request) {
        var comentario = new ComentarioResumoImpl();
        comentario.setComentario(request.getComentario());
        comentario.setDataCriacao(LocalDateTime.now());
        comentario.setUsuarioId(String.valueOf(request.getUsuarioId()));
        comentario.setEventoId(String.valueOf(request.getEventoId()));
        
        if (request.getComentarioPaiId() != null) {
            comentario.setComentarioPaiId(String.valueOf(request.getComentarioPaiId()));
        }

        comentarioRepositorio.salvar(comentario);
    }

    public void responderComentario(ResponderComentarioRequest request) {
        // Verifica se o comentário pai existe
        var comentarioPai = comentarioRepositorio.buscarPorId(request.getComentarioPaiId())
            .orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado"));

        // Cria a resposta como um novo comentário
        var resposta = new ComentarioResumoImpl();
        resposta.setComentario(request.getComentario());
        resposta.setDataCriacao(LocalDateTime.now());
        resposta.setUsuarioId(String.valueOf(request.getUsuarioId()));
        resposta.setEventoId(String.valueOf(request.getEventoId()));
        resposta.setComentarioPaiId(String.valueOf(request.getComentarioPaiId()));

        comentarioRepositorio.salvar(resposta);
    }

    public List<ComentarioResumo> listarComentariosPorEvento(Integer eventoId) {
        return comentarioRepositorio.listarPorEvento(eventoId);
    }

    public List<ComentarioResumo> listarComentariosPorUsuario(Integer usuarioId) {
        return comentarioRepositorio.listarPorUsuario(usuarioId);
    }

    public List<ComentarioResumo> listarRespostas(Integer comentarioPaiId) {
        return comentarioRepositorio.listarRespostas(comentarioPaiId);
    }
} 