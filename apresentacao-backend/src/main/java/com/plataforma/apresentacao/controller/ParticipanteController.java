package com.plataforma.apresentacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import com.plataforma.aplicacao.participante.ParticipanteServicoAplicacao;
import com.plataforma.aplicacao.participante.ParticipanteResumo;
import com.plataforma.aplicacao.participante.CriarParticipacaoRequest;
import com.plataforma.aplicacao.participante.AtualizarStatusParticipanteRequest;

@RestController
@RequestMapping("/participante")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteServicoAplicacao participanteServico;

    @PostMapping
    public ResponseEntity<ParticipanteResumo> criarParticipacao(
            @Valid @RequestBody CriarParticipacaoRequest request) {
        var participacao = participanteServico.criarParticipacao(
            request.getEventoId(), 
            request.getUsuarioId(), 
            request.getStatus()
        );
        return ResponseEntity.ok(participacao);
    }

    @PutMapping("/{eventoId}/{usuarioId}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Integer eventoId,
            @PathVariable Integer usuarioId,
            @Valid @RequestBody AtualizarStatusParticipanteRequest request) {
        participanteServico.atualizarStatus(eventoId, usuarioId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ParticipanteResumo>> listarPorEvento(@PathVariable Integer eventoId) {
        var participantes = participanteServico.listarPorEvento(eventoId);
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ParticipanteResumo>> listarPorUsuario(@PathVariable Integer usuarioId) {
        var participantes = participanteServico.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{eventoId}/{usuarioId}")
    public ResponseEntity<ParticipanteResumo> buscarParticipacao(
            @PathVariable Integer eventoId,
            @PathVariable Integer usuarioId) {
        var participacao = participanteServico.buscarParticipacao(eventoId, usuarioId);
        return ResponseEntity.ok(participacao);
    }
} 