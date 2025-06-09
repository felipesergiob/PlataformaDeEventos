package com.plataforma.apresentacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plataforma.aplicacao.usuario.UsuarioResumo;
import com.plataforma.aplicacao.usuario.UsuarioResumoImpl;
import com.plataforma.aplicacao.usuario.UsuarioServicoAplicacao;
import com.plataforma.aplicacao.usuario.dto.SeguirUsuarioRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@CrossOrigin
public class UsuarioController {

    private final UsuarioServicoAplicacao usuarioServico;

    @PostMapping("/register")
    public ResponseEntity<Void> registrar(@RequestBody UsuarioResumoImpl usuario) {
        usuarioServico.registrar(usuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResumo> login(@RequestBody LoginRequest request) {
        var usuario = usuarioServico.login(request.email(), request.senha());
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResumo> buscarPorId(@PathVariable Integer id) {
        var usuario = usuarioServico.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/seguir")
    public ResponseEntity<Void> seguirUsuario(@RequestBody SeguirUsuarioRequest request) {
        try {
            usuarioServico.seguirUsuario(request.seguidorId(), request.seguidoId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/deixar-de-seguir")
    public ResponseEntity<Void> deixarDeSeguir(@RequestBody SeguirUsuarioRequest request) {
        try {
            usuarioServico.deixarDeSeguir(request.seguidorId(), request.seguidoId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/seguidos")
    public ResponseEntity<java.util.List<UsuarioResumo>> listarSeguidos(@PathVariable Integer id) {
        var seguidos = usuarioServico.listarSeguidos(id);
        return ResponseEntity.ok(seguidos);
    }

    record LoginRequest(String email, String senha) {}
} 