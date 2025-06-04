package com.plataforma.apresentacao.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import com.plataforma.aplicacao.avaliacao.AvaliacaoServicoAplicacao;
import com.plataforma.aplicacao.avaliacao.AvaliacaoResumo;
import com.plataforma.aplicacao.avaliacao.CriarAvaliacaoRequest;

@RestController
@RequestMapping("/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoServicoAplicacao avaliacaoServico;

    @PostMapping
    public void avaliarEvento(@Valid @RequestBody CriarAvaliacaoRequest request) {
        avaliacaoServico.avaliarEvento(request);
    }

    @GetMapping("/evento/{eventoId}")
    public List<AvaliacaoResumo> listarAvaliacoesPorEvento(@PathVariable Integer eventoId) {
        return avaliacaoServico.listarAvaliacoesPorEvento(eventoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<AvaliacaoResumo> listarAvaliacoesPorUsuario(@PathVariable Integer usuarioId) {
        return avaliacaoServico.listarAvaliacoesPorUsuario(usuarioId);
    }

    @GetMapping
    public List<AvaliacaoResumo> listarTodasAvaliacoes() {
        return avaliacaoServico.listarTodasAvaliacoes();
    }
} 