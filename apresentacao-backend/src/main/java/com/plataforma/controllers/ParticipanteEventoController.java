package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.evento.ParticipanteEventoJpa;
import com.plataforma.persistencia.jpa.evento.ParticipanteEventoJpaRepositorio;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.evento.EventoJpaRepositorio;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
@RequiredArgsConstructor
public class ParticipanteEventoController {
    private final ParticipanteEventoJpaRepositorio participanteEventoJpaRepositorio;
    private final EventoJpaRepositorio eventoJpaRepositorio;
    private final UsuarioJpaRepositorio usuarioJpaRepositorio;

    @PostMapping("/evento/{eventoId}/usuario/{usuarioId}")
    public ResponseEntity<Void> confirmarPresenca(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId,
            @RequestParam String status) {
        ParticipanteEventoJpa participante = new ParticipanteEventoJpa();
        participante.setEvento(eventoJpaRepositorio.findById(eventoId).orElse(null));
        participante.setUsuario(usuarioJpaRepositorio.findById(usuarioId).orElse(null));
        participante.setStatus(status);
        participanteEventoJpaRepositorio.save(participante);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/evento/{eventoId}/usuario/{usuarioId}")
    public ResponseEntity<Void> cancelarPresenca(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId) {
        participanteEventoJpaRepositorio.deleteByEventoIdAndUsuarioId(eventoId, usuarioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}/eventos-futuros")
    public ResponseEntity<List<EventoResponseDTO>> listarEventosFuturosPorUsuario(@PathVariable Long usuarioId) {
        List<ParticipanteEventoJpa> participantes = participanteEventoJpaRepositorio
                .findByUsuarioIdAndEventoDataInicioAfter(usuarioId);
        List<EventoResponseDTO> response = participantes.stream()
                .map(participante -> {
                    EventoJpa evento = participante.getEvento();
                    EventoResponseDTO dto = new EventoResponseDTO();
                    dto.setId(evento.getId());
                    dto.setTitulo(evento.getTitulo());
                    dto.setDescricao(evento.getDescricao());
                    dto.setDataInicio(evento.getDataInicio());
                    dto.setDataFim(evento.getDataFim());
                    dto.setLocal(evento.getLocal());
                    dto.setGenero(evento.getGenero());
                    dto.setValor(evento.getValor());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<PerfilUsuarioResponseDTO>> listarParticipantesPorEvento(@PathVariable Long eventoId) {
        List<ParticipanteEventoJpa> participantes = participanteEventoJpaRepositorio.findByEventoId(eventoId);
        List<PerfilUsuarioResponseDTO> response = participantes.stream()
                .map(participante -> {
                    UsuarioJpa usuario = participante.getUsuario();
                    PerfilUsuarioResponseDTO dto = new PerfilUsuarioResponseDTO();
                    dto.setId(usuario.getId());
                    dto.setNome(usuario.getNome());
                    dto.setEmail(usuario.getEmail());
                    dto.setFoto(usuario.getFotoPerfil());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}