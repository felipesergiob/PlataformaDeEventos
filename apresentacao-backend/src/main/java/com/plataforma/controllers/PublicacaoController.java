package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.evento.PublicacaoJpa;
import com.plataforma.persistencia.jpa.evento.PublicacaoJpaRepositorio;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publicacoes")
@RequiredArgsConstructor
public class PublicacaoController {
    private final PublicacaoJpaRepositorio publicacaoJpaRepositorio;

    @PostMapping
    public ResponseEntity<PublicacaoResponseDTO> criarPublicacao(@RequestBody CriarPublicacaoRequestDTO request) {
        PublicacaoJpa publicacao = new PublicacaoJpa();
        publicacao.setEvento(new EventoJpa(request.getEventoId()));
        publicacao.setUsuario(new UsuarioJpa(request.getUsuarioId()));
        publicacao.setConteudo(request.getConteudo());
        publicacao.setFotos(request.getFotos());
        publicacao.setDataCriacao(LocalDateTime.now());
        
        publicacao = publicacaoJpaRepositorio.save(publicacao);

        PublicacaoResponseDTO response = new PublicacaoResponseDTO();
        response.setId(publicacao.getId());
        response.setEventoId(publicacao.getEvento().getId());
        response.setTituloEvento(publicacao.getEvento().getNome());
        response.setUsuarioId(publicacao.getUsuario().getId());
        response.setNomeUsuario(publicacao.getUsuario().getNome());
        response.setConteudo(publicacao.getConteudo());
        response.setFotos(publicacao.getFotos());
        response.setDataCriacao(publicacao.getDataCriacao());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<PublicacaoResponseDTO>> listarPublicacoesPorEvento(@PathVariable Long eventoId) {
        List<PublicacaoJpa> publicacoes = publicacaoJpaRepositorio.findByEventoId(eventoId);
        List<PublicacaoResponseDTO> response = publicacoes.stream()
                .map(publicacao -> {
                    PublicacaoResponseDTO dto = new PublicacaoResponseDTO();
                    dto.setId(publicacao.getId());
                    dto.setEventoId(publicacao.getEvento().getId());
                    dto.setTituloEvento(publicacao.getEvento().getNome());
                    dto.setUsuarioId(publicacao.getUsuario().getId());
                    dto.setNomeUsuario(publicacao.getUsuario().getNome());
                    dto.setConteudo(publicacao.getConteudo());
                    dto.setFotos(publicacao.getFotos());
                    dto.setDataCriacao(publicacao.getDataCriacao());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PublicacaoResponseDTO>> listarPublicacoesPorUsuario(@PathVariable Long usuarioId) {
        List<PublicacaoJpa> publicacoes = publicacaoJpaRepositorio.findByUsuarioId(usuarioId);
        List<PublicacaoResponseDTO> response = publicacoes.stream()
                .map(publicacao -> {
                    PublicacaoResponseDTO dto = new PublicacaoResponseDTO();
                    dto.setId(publicacao.getId());
                    dto.setEventoId(publicacao.getEvento().getId());
                    dto.setTituloEvento(publicacao.getEvento().getNome());
                    dto.setUsuarioId(publicacao.getUsuario().getId());
                    dto.setNomeUsuario(publicacao.getUsuario().getNome());
                    dto.setConteudo(publicacao.getConteudo());
                    dto.setFotos(publicacao.getFotos());
                    dto.setDataCriacao(publicacao.getDataCriacao());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
} 