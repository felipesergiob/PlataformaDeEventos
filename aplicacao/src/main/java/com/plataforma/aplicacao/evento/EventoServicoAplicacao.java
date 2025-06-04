package com.plataforma.aplicacao.evento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServicoAplicacao {
    
    private final EventoRepositorioAplicacao repositorio;
    private final UsuarioRepositorioAplicacao usuarioRepositorio;
    private static final int LIMITE_EVENTOS_DESTAQUE = 3;

    @Transactional
    public EventoResumo criar(CriarEventoRequest request) {
        var organizador = usuarioRepositorio.buscarPorId(request.getOrganizadorId())
            .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));

        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }

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

        repositorio.salvar(evento);
        return evento;
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
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado ou você não tem permissão para acessá-lo"));
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
            filtro.getGratuito()
        );
    }
} 