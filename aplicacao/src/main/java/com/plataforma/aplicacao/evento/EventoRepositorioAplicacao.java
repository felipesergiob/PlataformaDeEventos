package com.plataforma.aplicacao.evento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepositorioAplicacao {
    void salvar(EventoResumo evento);
    Optional<EventoResumo> buscarPorId(Integer id);
    List<EventoResumo> listarTodos();
    List<EventoResumo> listarPorOrganizador(Integer organizadorId);
    List<EventoDestaqueResumo> listarEventosDestaqueDaSemana(int limite);
    List<EventoDashboardResumo> listarDashboardPorOrganizador(Integer organizadorId);
    Optional<EventoDashboardResumo> buscarDashboardEvento(Integer eventoId, Integer organizadorId);
    List<EventoResumo> buscarPorOrganizador(Integer organizadorId);
    Optional<EventoResumo> buscarPorIdEOrganizador(Integer id, Integer organizadorId);
    List<EventoResumo> buscarEventosComFiltro(
        String genero,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Double precoMinimo,
        Double precoMaximo,
        String periodoHorario,
        Boolean gratuito
    );
} 