package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import static org.apache.commons.lang3.Validate.notNull;
import com.plataforma.compartilhado.UsuarioId;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import com.plataforma.evento.Evento.Status;

public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        notNull(eventoRepository, "O repositório de eventos não pode ser nulo");
        this.eventoRepository = eventoRepository;
    }

    public void salvar(Evento evento) {
        notNull(evento, "O evento não pode ser nulo");
        eventoRepository.salvar(evento);
    }

    public Evento obter(EventoId id) {
        notNull(id, "O ID do evento não pode ser nulo");
        Evento evento = eventoRepository.obter(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        return evento;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.listarTodos();
    }

    public List<Evento> listarPorOrganizador(UsuarioId organizador) {
        notNull(organizador, "O organizador não pode ser nulo");
        return eventoRepository.listarPorOrganizador(organizador);
    }

    public List<Evento> listarPorFiltros(String genero, LocalTime horarioInicio, LocalTime horarioFim, LocalDate data, BigDecimal preco) {
        List<Evento> eventos = eventoRepository.listarTodos();
        
        if (genero != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getGenero().equals(genero))
                .collect(Collectors.toList());
        }
        
        if (horarioInicio != null && horarioFim != null) {
            eventos = eventos.stream()
                .filter(evento -> {
                    LocalTime horarioEvento = evento.getDataInicio().toLocalTime();
                    return !horarioEvento.isBefore(horarioInicio) && !horarioEvento.isAfter(horarioFim);
                })
                .collect(Collectors.toList());
        }
        
        if (data != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getDataInicio().toLocalDate().equals(data))
                .collect(Collectors.toList());
        }
        
        if (preco != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getValor().compareTo(preco) == 0)
                .collect(Collectors.toList());
        }
        
        return eventos;
    }

    public List<Evento> listarSalvos(UsuarioId id) {
        notNull(id, "O ID do usuário não pode ser nulo");
        List<Evento> eventos = eventoRepository.listarSalvos(id);
        
        return eventos.stream()
            .filter(evento -> evento.getDataInicio().isAfter(LocalDateTime.now()))
            .filter(evento -> evento.getStatus() == Status.SALVO)
            .collect(Collectors.toList());
    }

    public List<Evento> listarConfirmados(UsuarioId id) {
        notNull(id, "O ID do usuário não pode ser nulo");
        List<Evento> eventos = eventoRepository.listarConfirmados(id);
        
        return eventos.stream()
            .filter(evento -> evento.getStatus() == Status.CONFIRMADO)
            .collect(Collectors.toList());
    }
} 