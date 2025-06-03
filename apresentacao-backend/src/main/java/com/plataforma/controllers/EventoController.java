package com.plataforma.controllers;

import com.plataforma.dto.*;
import com.plataforma.persistencia.jpa.evento.EventoJpa;
import com.plataforma.persistencia.jpa.evento.EventoJpaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoJpaRepositorio eventoJpaRepositorio;

    private int[] getHorarioPeriodo(String periodoDia) {
        switch (periodoDia.toLowerCase()) {
            case "manha":
                return new int[]{6, 12};
            case "tarde":
                return new int[]{12, 18};
            case "noite":
                return new int[]{18, 24};
            case "madrugada":
                return new int[]{0, 6};
            default:
                throw new IllegalArgumentException("Período do dia inválido: " + periodoDia);
        }
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarEventos(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String periodoDia) {
        List<EventoJpa> eventos;
        if (genero != null && periodoDia != null) {
            int[] horario = getHorarioPeriodo(periodoDia);
            eventos = eventoJpaRepositorio.findByGeneroAndPeriodoDia(genero, horario[0], horario[1]);
        } else if (genero != null) {
            eventos = eventoJpaRepositorio.findByGenero(genero);
        } else if (periodoDia != null) {
            int[] horario = getHorarioPeriodo(periodoDia);
            eventos = eventoJpaRepositorio.findByPeriodoDia(horario[0], horario[1]);
        } else {
            eventos = eventoJpaRepositorio.findAll();
        }

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

    @PostMapping("/filtrar")
    public ResponseEntity<List<EventoResponseDTO>> filtrarEventos(@RequestBody FiltrarEventosRequestDTO request) {
        Integer horaInicio = null;
        Integer horaFim = null;
        if (request.getPeriodoDia() != null) {
            int[] horario = getHorarioPeriodo(request.getPeriodoDia());
            horaInicio = horario[0];
            horaFim = horario[1];
        }

        List<EventoJpa> eventos = eventoJpaRepositorio.filtrarEventos(
                request.getGenero(),
                request.getDataInicio(),
                request.getDataFim(),
                request.getValorMaximo(),
                horaInicio,
                horaFim
        );

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

    @GetMapping("/destaque")
    public ResponseEntity<List<EventoResponseDTO>> listarEventosDestaque() {
        LocalDateTime inicioSemana = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime fimSemana = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MAX);
        List<EventoJpa> eventos = eventoJpaRepositorio.findByDataInicioBetweenAndParticipantesGreaterThanOrderByParticipantesDesc(
            inicioSemana, fimSemana, 0);

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

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEvento(@PathVariable Long id) {
        EventoJpa evento = eventoJpaRepositorio.findById(id).orElse(null);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        EventoResponseDTO response = new EventoResponseDTO();

        response.setId(evento.getId());
        response.setTitulo(evento.getTitulo());
        response.setDescricao(evento.getDescricao());
        response.setDataInicio(evento.getDataInicio());
        response.setDataFim(evento.getDataFim());
        response.setLocal(evento.getLocal());
        response.setGenero(evento.getGenero());
        response.setValor(evento.getValor());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(@RequestBody CriarEventoRequestDTO request) {
        EventoJpa evento = new EventoJpa();

        evento.setTitulo(request.getTitulo());
        evento.setDescricao(request.getDescricao());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setLocal(request.getLocal());
        evento.setGenero(request.getGenero());
        evento.setValor(request.getValor());
        evento.setImagem(request.getImagem());
        evento.setParticipantes(request.getParticipantes());
        evento.setDataCriacao(LocalDateTime.now());

        evento = eventoJpaRepositorio.save(evento);

        EventoResponseDTO response = new EventoResponseDTO();

        response.setId(evento.getId());
        response.setTitulo(evento.getTitulo());
        response.setDescricao(evento.getDescricao());
        response.setDataInicio(evento.getDataInicio());
        response.setDataFim(evento.getDataFim());
        response.setLocal(evento.getLocal());
        response.setGenero(evento.getGenero());
        response.setValor(evento.getValor());

        return ResponseEntity.ok(response);
    }
} 