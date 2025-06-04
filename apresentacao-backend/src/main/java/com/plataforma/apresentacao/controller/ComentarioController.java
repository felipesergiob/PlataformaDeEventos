package com.plataforma.apresentacao.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import com.plataforma.aplicacao.comentario.ComentarioServicoAplicacao;
import com.plataforma.aplicacao.comentario.ComentarioResumo;
import com.plataforma.aplicacao.comentario.CriarComentarioRequest;
import com.plataforma.aplicacao.comentario.ResponderComentarioRequest;

@RestController
@RequestMapping("/comentario")
@RequiredArgsConstructor
public class ComentarioController {
    private final ComentarioServicoAplicacao comentarioServico;

    @PostMapping
    public void criarComentario(@Valid @RequestBody CriarComentarioRequest request) {
        comentarioServico.criarComentario(request);
    }

    @PostMapping("/responder")
    public void responderComentario(@Valid @RequestBody ResponderComentarioRequest request) {
        comentarioServico.responderComentario(request);
    }

    @GetMapping("/evento/{eventoId}")
    public List<ComentarioResumo> listarComentariosPorEvento(@PathVariable Integer eventoId) {
        return comentarioServico.listarComentariosPorEvento(eventoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ComentarioResumo> listarComentariosPorUsuario(@PathVariable Integer usuarioId) {
        return comentarioServico.listarComentariosPorUsuario(usuarioId);
    }

    @GetMapping("/respostas/{comentarioPaiId}")
    public List<ComentarioResumo> listarRespostas(@PathVariable Integer comentarioPaiId) {
        return comentarioServico.listarRespostas(comentarioPaiId);
    }
} 