package com.plataforma.aplicacao.evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepositorioAplicacao {
    void salvar(EventoResumo evento);
    Optional<EventoResumo> buscarPorId(Integer id);
    List<EventoResumo> listarTodos();
    List<EventoResumo> listarPorOrganizador(Integer organizadorId);
} 