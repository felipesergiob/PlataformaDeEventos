package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import com.plataforma.persistencia.jpa.usuario.UsuarioJpaRepositorio;
import com.plataforma.persistencia.jpa.usuario.SeguidorJpa;
import com.plataforma.persistencia.jpa.usuario.SeguidorJpaRepositorio;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.evento.EventoJpaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioJpaRepositorio usuarioJpaRepositorio;
    private final SeguidorJpaRepositorio seguidorJpaRepositorio;
    private final EventoJpaRepositorio eventoJpaRepositorio;
    @GetMapping("/{id}")
    public ResponseEntity<PerfilUsuarioResponseDTO> buscarPerfilUsuario(@PathVariable Long id) {
        UsuarioJpa usuario = usuarioJpaRepositorio.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        PerfilUsuarioResponseDTO response = new PerfilUsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setFoto(usuario.getFotoPerfil());
        response.setTotalEventosOrganizados(eventoJpaRepositorio.findByOrganizadorId(id).size());
        response.setMediaAvaliacao(eventoJpaRepositorio.findMediaAvaliacaoByOrganizadorId(id));
        response.setTotalSeguidores(usuario.getSeguidores());
        response.setTotalSeguindo(usuario.getSeguindo().size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/eventos-passados")
    public ResponseEntity<List<EventoResponseDTO>> listarEventosPassados(@PathVariable Long id) {
        List<EventoJpa> eventos = usuarioJpaRepositorio.findEventosPassadosByUsuarioId(id);
        List<EventoResponseDTO> response = eventos.stream()
                .map(evento -> {
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

    @GetMapping("/{id}/eventos-futuros")
    public ResponseEntity<List<EventoResponseDTO>> listarEventosFuturos(@PathVariable Long id) {
        List<EventoJpa> eventos = usuarioJpaRepositorio.findEventosFuturosByUsuarioId(id);
        List<EventoResponseDTO> response = eventos.stream()
                .map(evento -> {
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

    @PostMapping("/{id}/seguir/{seguidoId}")
    public ResponseEntity<Void> seguirUsuario(@PathVariable Long id, @PathVariable Long seguidoId) {
        SeguidorJpa seguidor = new SeguidorJpa();
        seguidor.setSeguidor(usuarioJpaRepositorio.findById(id).orElse(null));
        seguidor.setSeguido(usuarioJpaRepositorio.findById(seguidoId).orElse(null));
        seguidorJpaRepositorio.save(seguidor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/seguir/{seguidoId}")
    public ResponseEntity<Void> deixarDeSeguirUsuario(@PathVariable Long id, @PathVariable Long seguidoId) {
        seguidorJpaRepositorio.deleteBySeguidorIdAndSeguidoId(id, seguidoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/seguidores")
    public ResponseEntity<List<PerfilUsuarioResponseDTO>> listarSeguidores(@PathVariable Long id) {
        List<UsuarioJpa> seguidores = usuarioJpaRepositorio.findSeguidoresByUsuarioId(id);
        List<PerfilUsuarioResponseDTO> response = seguidores.stream()
                .map(usuario -> {
                    PerfilUsuarioResponseDTO dto = new PerfilUsuarioResponseDTO();
                    dto.setId(usuario.getId());
                    dto.setNome(usuario.getNome());
                    dto.setEmail(usuario.getEmail());
                    dto.setFoto(usuario.getFoto());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seguindo")
    public ResponseEntity<List<PerfilUsuarioResponseDTO>> listarSeguindo(@PathVariable Long id) {
        List<UsuarioJpa> seguindo = usuarioJpaRepositorio.findSeguindoByUsuarioId(id);
        List<PerfilUsuarioResponseDTO> response = seguindo.stream()
                .map(usuario -> {
                    PerfilUsuarioResponseDTO dto = new PerfilUsuarioResponseDTO();
                    dto.setId(usuario.getId());
                    dto.setNome(usuario.getNome());
                    dto.setEmail(usuario.getEmail());
                    dto.setFoto(usuario.getFoto());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
} 