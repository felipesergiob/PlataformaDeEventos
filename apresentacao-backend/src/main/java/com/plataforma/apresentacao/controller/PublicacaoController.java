package com.plataforma.apresentacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plataforma.aplicacao.publicacao.CriarPublicacaoRequest;
import com.plataforma.aplicacao.publicacao.PublicacaoResumo;
import com.plataforma.aplicacao.publicacao.PublicacaoServicoAplicacao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/publicacao")
@RequiredArgsConstructor
public class PublicacaoController {

    private final PublicacaoServicoAplicacao publicacaoServico;

    @PostMapping
    public ResponseEntity<PublicacaoResumo> criar(@RequestBody @Valid CriarPublicacaoRequest request) {
        var publicacao = publicacaoServico.criar(request);
        return ResponseEntity.ok(publicacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacaoResumo> buscarPorId(@PathVariable Integer id) {
        var publicacao = publicacaoServico.buscarPorId(id);
        return ResponseEntity.ok(publicacao);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<PublicacaoResumo>> listarPorEvento(@PathVariable Integer eventoId) {
        var publicacoes = publicacaoServico.listarPorEvento(eventoId);
        return ResponseEntity.ok(publicacoes);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PublicacaoResumo>> listarPorUsuario(@PathVariable Integer usuarioId) {
        var publicacoes = publicacaoServico.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(publicacoes);
    }
} 