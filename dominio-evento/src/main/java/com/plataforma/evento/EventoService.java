package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

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

    public List<Evento> listarAtivos() {
        return eventoRepository.listarAtivos();
    }

    public List<Evento> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        notNull(inicio, "A data de início não pode ser nula");
        notNull(fim, "A data de fim não pode ser nula");
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }
        return eventoRepository.listarPorPeriodo(inicio, fim);
    }

    public List<Evento> listarPorCategoria(String categoria) {
        notNull(categoria, "A categoria não pode ser nula");
        return eventoRepository.listarPorCategoria(categoria);
    }

    public List<Evento> listarPorGenero(String genero) { //ISSO NAO TESTA NADA APENAS O MOCK
        notNull(genero, "O gênero não pode ser nulo");
        return eventoRepository.listarPorGenero(genero);
    }

    public List<Evento> listarPorOrganizador(String organizador) {
        notNull(organizador, "O organizador não pode ser nulo");
        return eventoRepository.listarPorOrganizador(organizador);
    }

    public List<Evento> listarPorValor(BigDecimal valorMinimo, BigDecimal valorMaximo) {
        notNull(valorMinimo, "O valor mínimo não pode ser nulo");
        notNull(valorMaximo, "O valor máximo não pode ser nulo");
        if (valorMinimo.compareTo(valorMaximo) > 0) {
            throw new IllegalArgumentException("O valor mínimo não pode ser maior que o valor máximo");
        }
        return eventoRepository.listarPorValor(valorMinimo, valorMaximo);
    }

    public List<Evento> listarComVagasDisponiveis() {
        return eventoRepository.listarTodos().stream()
            .filter(Evento::temVagasDisponiveis)
            .toList();
    }

    public void excluir(EventoId id) {
        notNull(id, "O ID do evento não pode ser nulo");
        if (!eventoRepository.existe(id)) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        eventoRepository.excluir(id);
    }

    public void desativar(EventoId id) {
        Evento evento = obter(id);
        evento.desativar();
        eventoRepository.salvar(evento);
    }

    public void ativar(EventoId id) {
        Evento evento = obter(id);
        evento.ativar();
        eventoRepository.salvar(evento);
    }

    public void cancelar(EventoId id) {
        Evento evento = obter(id);
        evento.cancelar();
        eventoRepository.salvar(evento);
    }

    public void incrementarInscritos(EventoId id) {
        Evento evento = obter(id);
        evento.incrementarInscritos();
        eventoRepository.salvar(evento);
    }

    public void decrementarInscritos(EventoId id) {
        Evento evento = obter(id);
        evento.decrementarInscritos();
        eventoRepository.salvar(evento);
    }

    public List<Evento> listarPorStatus(Evento.StatusEvento status) {
        notNull(status, "O status não pode ser nulo");
        return eventoRepository.listarPorStatus(status);
    }

    public List<Evento> listarProximos() {
        return eventoRepository.listarTodos().stream()
            .filter(evento -> evento.getStatus() == Evento.StatusEvento.AGENDADO)
            .toList();
    }

    public List<Evento> listarEmAndamento() {
        return eventoRepository.listarTodos().stream()
            .filter(evento -> evento.getStatus() == Evento.StatusEvento.EM_ANDAMENTO)
            .toList();
    }

    public List<Evento> listarFinalizados() {
        return eventoRepository.listarTodos().stream()
            .filter(evento -> evento.getStatus() == Evento.StatusEvento.FINALIZADO)
            .toList();
    }

    public List<Evento> listarPorHorario(LocalTime horarioInicio, LocalTime horarioFim) {
        notNull(horarioInicio, "O horário de início não pode ser nulo");
        notNull(horarioFim, "O horário de fim não pode ser nulo");
        if (horarioInicio.isAfter(horarioFim)) {
            throw new IllegalArgumentException("O horário de início não pode ser posterior ao horário de fim");
        }
        return eventoRepository.listarPorHorario(horarioInicio, horarioFim);
    }

    public List<Evento> listarPorData(LocalDate data) {
        notNull(data, "A data não pode ser nula");
        return eventoRepository.listarPorData(data);
    }

    public List<Evento> listarPorPreco(BigDecimal preco) {
        notNull(preco, "O preço não pode ser nulo");
        if (preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        return eventoRepository.listarPorPreco(preco);
    }

    public List<Evento> listarPorNumeroInscritos(int limite) {
        isTrue(limite > 0, "O limite deve ser maior que zero");
        return eventoRepository.listarPorNumeroInscritos(limite);
    }

    public List<Evento> listarPorFiltros(String genero, LocalTime horarioInicio, LocalTime horarioFim, LocalDate data, BigDecimal preco) {
        List<Evento> eventos = eventoRepository.listarTodos();
        
        if (genero != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getGenero().equals(genero))
                .toList();
        }
        
        if (horarioInicio != null && horarioFim != null) {
            eventos = eventos.stream()
                .filter(evento -> {
                    LocalTime horarioEvento = evento.getDataInicio().toLocalTime();
                    return !horarioEvento.isBefore(horarioInicio) && !horarioEvento.isAfter(horarioFim);
                })
                .toList();
        }
        
        if (data != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getDataInicio().toLocalDate().equals(data))
                .toList();
        }
        
        if (preco != null) {
            eventos = eventos.stream()
                .filter(evento -> evento.getValor().compareTo(preco) == 0)
                .toList();
        }
        
        return eventos;
    }

    public void criarEvento(Evento evento) {
        salvar(evento);
    }

    public List<Evento> filtrarEventosPorGenero(String genero) {
        return listarPorGenero(genero);
    }

    public List<Evento> filtrarEventosPorHorario(LocalDateTime horario) {
        return listarTodos().stream()
            .filter(evento -> evento.getDataInicio().toLocalTime().equals(horario.toLocalTime()))
            .toList();
    }

    public List<Evento> filtrarEventosPorData(LocalDateTime data) {
        return listarTodos().stream()
            .filter(evento -> evento.getDataInicio().toLocalDate().equals(data.toLocalDate()))
            .toList();
    }

    public List<Evento> filtrarEventosPorPrecoMaximo(BigDecimal precoMaximo) {
        return listarTodos().stream()
            .filter(evento -> evento.getValor().compareTo(precoMaximo) <= 0)
            .toList();
    }
} 