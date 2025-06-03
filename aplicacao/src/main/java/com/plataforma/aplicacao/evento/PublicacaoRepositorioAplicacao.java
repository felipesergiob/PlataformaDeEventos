package com.plataforma.aplicacao.evento;

import java.util.List;

public interface PublicacaoRepositorioAplicacao {
    void salvar(Publicacao publicacao);
    List<Publicacao> listarPorEvento(String eventoId);
    List<Publicacao> listarPorAutor(String autorId);
} 