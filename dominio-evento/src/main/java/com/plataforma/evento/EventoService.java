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
import java.time.DayOfWeek;
import com.plataforma.avaliacao.AvaliacaoService;
import com.plataforma.avaliacao.AvaliacaoRepository;
import com.plataforma.avaliacao.Avaliacao;
import java.util.HashMap;
import java.util.Map;

public class EventoService {
    private final EventoRepository eventoRepository;
    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoRepository avaliacaoRepository;

    public EventoService(EventoRepository eventoRepository, AvaliacaoService avaliacaoService, AvaliacaoRepository avaliacaoRepository) {
        notNull(eventoRepository, "O repositório de eventos não pode ser nulo");
        notNull(avaliacaoService, "O serviço de avaliações não pode ser nulo");
        this.eventoRepository = eventoRepository;
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
        this.avaliacaoService = null;
        this.avaliacaoRepository = null;
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

    public List<Evento> listarPorFiltros(String genero, LocalTime horarioInicio, LocalTime horarioFim, LocalDate data,
            BigDecimal preco) {
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

    public List<Evento> listarEventosDestaques() {
        List<Evento> todosEventos = eventoRepository.listarTodos();

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioSemana = agora.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fimSemana = inicioSemana.plusDays(7);

        return todosEventos.stream()
                .filter(evento -> {
                    LocalDateTime dataEvento = evento.getDataInicio();
                    return dataEvento.isAfter(inicioSemana) && dataEvento.isBefore(fimSemana);
                })
                .sorted((evento1, evento2) -> evento2.getInscritos().compareTo(evento1.getInscritos()))
                .limit(3)
                .toList();
    }

    public Map<String, Object> dashboardEvento(EventoId id) {
        Evento evento = eventoRepository.obter(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        if (evento.getDataFim().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("O dashboard só está disponível para eventos encerrados");
        }

        Map<String, Object> dashboard = new HashMap<>();
        int totalInscritos = evento.getInscritos();
        double mediaAvaliacoes = avaliacaoService.obterMediaAvaliacoes(id);
        List<Avaliacao> avaliacoes = avaliacaoRepository.listarNotasEvento(id);
        int quantidadeNotasAvaliacoes = avaliacoes.size();

        List<String> comentarios = avaliacoes.stream()
            .map(Avaliacao::getComentario)
            .filter(comentario -> comentario != null && !comentario.trim().isEmpty())
            .toList();

        dashboard.put("nomeEvento", evento.getNome());
        dashboard.put("totalInscritos", totalInscritos);
        dashboard.put("mediaAvaliacoes", mediaAvaliacoes);
        dashboard.put("quantidadeAvaliacoes", quantidadeNotasAvaliacoes);
        dashboard.put("comentarios", comentarios);

        return dashboard;
    }
}