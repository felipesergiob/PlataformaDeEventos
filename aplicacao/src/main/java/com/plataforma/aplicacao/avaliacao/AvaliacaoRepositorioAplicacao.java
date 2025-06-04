package com.plataforma.aplicacao.avaliacao;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepositorioAplicacao {
    void salvar(AvaliacaoResumo avaliacao);
    Optional<AvaliacaoResumo> buscarPorId(Integer id);
    List<AvaliacaoResumo> listarPorEvento(Integer eventoId);
    List<AvaliacaoResumo> listarPorUsuario(Integer usuarioId);
    Optional<AvaliacaoResumo> buscarPorEventoEUsuario(Integer eventoId, Integer usuarioId);
    List<AvaliacaoResumo> listarTodas();
} 