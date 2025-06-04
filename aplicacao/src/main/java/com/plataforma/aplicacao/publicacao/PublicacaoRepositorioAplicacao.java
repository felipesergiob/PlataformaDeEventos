package com.plataforma.aplicacao.publicacao;

import java.util.List;
import java.util.Optional;

public interface PublicacaoRepositorioAplicacao {
    void salvar(PublicacaoResumo publicacao);
    Optional<PublicacaoResumo> buscarPorId(Integer id);
    List<PublicacaoResumo> listarPorEvento(Integer eventoId);
    List<PublicacaoResumo> listarPorUsuario(Integer usuarioId);
} 