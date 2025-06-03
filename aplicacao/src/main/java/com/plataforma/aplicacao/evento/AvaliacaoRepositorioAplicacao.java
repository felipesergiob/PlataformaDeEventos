package com.plataforma.aplicacao.evento;

import java.util.List;

public interface AvaliacaoRepositorioAplicacao {
    void salvar(Avaliacao avaliacao);
    List<Avaliacao> listarPorEvento(String eventoId);
    void salvarResposta(String eventoId, String avaliacaoId, String resposta);
} 