package com.plataforma.aplicacao.evento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;
import com.plataforma.aplicacao.evento.observer.EventoNotificacao;
import com.plataforma.aplicacao.evento.observer.EventoObserver;
import com.plataforma.aplicacao.evento.chain.ValidadorEvento;
import com.plataforma.aplicacao.evento.strategy.FiltroEventoStrategy;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventoServicoAplicacao {

    private final EventoRepositorioAplicacao repositorio;
    private final UsuarioRepositorioAplicacao usuarioRepositorio;
    private final List<EventoObserver> observers;
    private final ValidadorEvento validadorEvento;
    private final Map<String, FiltroEventoStrategy> estrategiasFiltro;

    private static final int LIMITE_EVENTOS_DESTAQUE = 3;

    @Transactional
    public EventoResumo criar(CriarEventoRequest request) {
        var organizador = usuarioRepositorio.buscarPorId(request.getOrganizadorId())
                .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));

        var evento = new EventoResumoImpl();
        evento.setTitulo(request.getTitulo());
        evento.setDescricao(request.getDescricao());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setLocal(request.getLocal());
        evento.setGenero(request.getGenero());
        evento.setValor(request.getValor());
        evento.setImagem(request.getImagem());
        evento.setParticipantes(0);
        evento.setDataCriacao(java.time.LocalDateTime.now());
        evento.setOrganizadorId(organizador.getId());

        validadorEvento.validar(evento);

        repositorio.salvar(evento);

        notificarObservadores(evento, "CRIACAO", "Novo evento criado: " + evento.getTitulo());

        return evento;
    }

    @Transactional
    public void realizarInscricao(Integer eventoId, Integer participanteId) {
        var evento = buscarPorId(eventoId);

        validadorEvento.validar(evento);

        notificarObservadores(evento, "INSCRICAO",
                "Nova inscrição realizada para o evento: " + evento.getTitulo());
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> filtrarEventos(String estrategia, List<EventoResumo> eventos) {
        FiltroEventoStrategy filtro = estrategiasFiltro.get(estrategia);
        if (filtro != null) {
            return filtro.filtrar(eventos);
        }
        return eventos;
    }

    private void notificarObservadores(EventoResumo evento, String tipo, String mensagem) {
        EventoNotificacao notificacao = new EventoNotificacao(evento, tipo, mensagem);
        observers.forEach(observer -> observer.notificar(notificacao));
    }

    @Transactional(readOnly = true)
    public EventoResumo buscarPorId(Integer id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> listarTodos() {
        return repositorio.listarTodos();
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> listarPorOrganizador(Integer organizadorId) {
        return repositorio.listarPorOrganizador(organizadorId);
    }

    @Transactional(readOnly = true)
    public List<EventoDestaqueResumo> listarEventosDestaqueDaSemana() {
        return repositorio.listarEventosDestaqueDaSemana(LIMITE_EVENTOS_DESTAQUE);
    }

    public List<EventoDashboardResumo> listarDashboardEventosOrganizador(Integer organizadorId) {
        return repositorio.listarDashboardPorOrganizador(organizadorId);
    }

    public EventoDashboardResumo buscarDashboardEvento(Integer eventoId, Integer organizadorId) {
        return repositorio.buscarDashboardEvento(eventoId, organizadorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Evento não encontrado ou você não tem permissão para acessá-lo"));
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> buscarEventosComFiltro(EventoFiltro filtro) {
        return repositorio.buscarEventosComFiltro(
                filtro.getGenero(),
                filtro.getDataInicio(),
                filtro.getDataFim(),
                filtro.getPrecoMinimo(),
                filtro.getPrecoMaximo(),
                filtro.getPeriodoHorario(),
                filtro.getGratuito());
    }
}