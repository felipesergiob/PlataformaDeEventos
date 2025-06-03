package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.evento.ComentarioJpa;
import com.plataforma.persistencia.jpa.evento.ComentarioJpaRepositorio;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.evento.EventoJpaRepositorio;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpaRepositorio;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {
    private final ComentarioJpaRepositorio comentarioJpaRepositorio;
    private final EventoJpaRepositorio eventoJpaRepositorio;
    private final UsuarioJpaRepositorio usuarioJpaRepositorio;
    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody CriarComentarioRequestDTO request) {
        ComentarioJpa comentario = new ComentarioJpa();
        comentario.setEvento(eventoJpaRepositorio.findById(request.getEventoId()).orElse(null));
        comentario.setUsuario(usuarioJpaRepositorio.findById(request.getUsuarioId()).orElse(null));
        comentario.setComentario(request.getComentario());
        comentario.setDataCriacao(LocalDateTime.now());
        
        if (request.getComentarioPaiId() != null) {
            comentario.setComentarioPai(comentarioJpaRepositorio.findById(request.getComentarioPaiId()).orElse(null));
        }
        
        comentario = comentarioJpaRepositorio.save(comentario);

        ComentarioResponseDTO response = new ComentarioResponseDTO();
        response.setId(comentario.getId());
        response.setEventoId(comentario.getEvento().getId());
        response.setUsuarioId(comentario.getUsuario().getId());
        response.setComentario(comentario.getComentario());
        response.setDataCriacao(comentario.getDataCriacao());
        response.setComentarioPaiId(comentario.getComentarioPai() != null ? comentario.getComentarioPai().getId() : null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentariosPorEvento(@PathVariable Long eventoId) {
        List<ComentarioJpa> comentarios = comentarioJpaRepositorio.findByEventoId(eventoId);
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(comentario -> {
                    ComentarioResponseDTO dto = new ComentarioResponseDTO();
                    dto.setId(comentario.getId());
                    dto.setEventoId(comentario.getEvento().getId());
                    dto.setUsuarioId(comentario.getUsuario().getId());
                    dto.setComentario(comentario.getComentario());
                    dto.setDataCriacao(comentario.getDataCriacao());
                    dto.setComentarioPaiId(comentario.getComentarioPai() != null ? comentario.getComentarioPai().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}/principais")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentariosPrincipaisPorEvento(@PathVariable Long eventoId) {
        List<ComentarioJpa> comentarios = comentarioJpaRepositorio.findComentariosPrincipaisByEventoId(eventoId);
        List<ComentarioResponseDTO> response = comentarios.stream()
                .map(comentario -> {
                    ComentarioResponseDTO dto = new ComentarioResponseDTO();
                    dto.setId(comentario.getId());
                    dto.setEventoId(comentario.getEvento().getId());
                    dto.setUsuarioId(comentario.getUsuario().getId());
                    dto.setComentario(comentario.getComentario());
                    dto.setDataCriacao(comentario.getDataCriacao());
                    dto.setComentarioPaiId(comentario.getComentarioPai() != null ? comentario.getComentarioPai().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{comentarioId}/respostas")
    public ResponseEntity<List<ComentarioResponseDTO>> listarRespostasPorComentario(@PathVariable Long comentarioId) {
        List<ComentarioJpa> respostas = comentarioJpaRepositorio.findByComentarioPaiId(comentarioId);
        List<ComentarioResponseDTO> response = respostas.stream()
                .map(comentario -> {
                    ComentarioResponseDTO dto = new ComentarioResponseDTO();
                    dto.setId(comentario.getId());
                    dto.setEventoId(comentario.getEvento().getId());
                    dto.setUsuarioId(comentario.getUsuario().getId());
                    dto.setComentario(comentario.getComentario());
                    dto.setDataCriacao(comentario.getDataCriacao());
                    dto.setComentarioPaiId(comentario.getComentarioPai() != null ? comentario.getComentarioPai().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
} 