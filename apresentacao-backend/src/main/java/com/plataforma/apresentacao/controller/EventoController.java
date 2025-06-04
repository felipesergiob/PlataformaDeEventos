package com.plataforma.apresentacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plataforma.aplicacao.evento.CriarEventoRequest;
import com.plataforma.aplicacao.evento.EventoResumo;
import com.plataforma.aplicacao.evento.EventoServicoAplicacao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {

    private final EventoServicoAplicacao eventoServico;

    @PostMapping
    public ResponseEntity<EventoResumo> criar(@RequestBody @Valid CriarEventoRequest request) {
        var evento = eventoServico.criar(request);
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResumo> buscarPorId(@PathVariable Integer id) {
        var evento = eventoServico.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @GetMapping
    public ResponseEntity<List<EventoResumo>> listarTodos() {
        var eventos = eventoServico.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/organizador/{organizadorId}")
    public ResponseEntity<List<EventoResumo>> listarPorOrganizador(@PathVariable Integer organizadorId) {
        var eventos = eventoServico.listarPorOrganizador(organizadorId);
        return ResponseEntity.ok(eventos);
    }
} 