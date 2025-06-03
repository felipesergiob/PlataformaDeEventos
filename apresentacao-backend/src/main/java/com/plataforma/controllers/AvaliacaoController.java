package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.evento.AvaliacaoJpa;
import com.plataforma.persistencia.jpa.evento.AvaliacaoJpaRepositorio;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.evento.EventoJpaRepositorio;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {
    private final AvaliacaoJpaRepositorio avaliacaoJpaRepositorio;
    private final EventoJpaRepositorio eventoJpaRepositorio;
    private final UsuarioJpaRepositorio usuarioJpaRepositorio;
    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@RequestBody CriarAvaliacaoRequestDTO request) {
        AvaliacaoJpa avaliacao = new AvaliacaoJpa();
        avaliacao.setEvento(eventoJpaRepositorio.findById(request.getEventoId()).orElse(null));
        avaliacao.setUsuario(usuarioJpaRepositorio.findById(request.getUsuarioId()).orElse(null));
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setDataCriacao(LocalDateTime.now());

        avaliacao = avaliacaoJpaRepositorio.save(avaliacao);

        AvaliacaoResponseDTO response = new AvaliacaoResponseDTO();
        response.setId(avaliacao.getId());
        response.setEventoId(avaliacao.getEvento().getId());
        response.setUsuarioId(avaliacao.getUsuario().getId());
        response.setNota(avaliacao.getNota());
        response.setComentario(avaliacao.getComentario());
        response.setDataCriacao(avaliacao.getDataCriacao());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoesPorEvento(@PathVariable Long eventoId) {
        List<AvaliacaoJpa> avaliacoes = avaliacaoJpaRepositorio.findByEventoId(eventoId);
        List<AvaliacaoResponseDTO> response = avaliacoes.stream()
                .map(avaliacao -> {
                    AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();
                    dto.setId(avaliacao.getId());
                    dto.setEventoId(avaliacao.getEvento().getId());
                    dto.setUsuarioId(avaliacao.getUsuario().getId());
                    dto.setNota(avaliacao.getNota());
                    dto.setComentario(avaliacao.getComentario());
                    dto.setDataCriacao(avaliacao.getDataCriacao());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}/media")
    public ResponseEntity<Double> obterMediaAvaliacoesPorEvento(@PathVariable Long eventoId) {
        Double media = avaliacaoJpaRepositorio.findMediaNotaByEventoId(eventoId);
        return ResponseEntity.ok(media);
    }
} 