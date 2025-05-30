package com.plataforma.Publicacao;

import com.plataforma.compartilhado.EventoId;
import java.util.List;

public interface PublicacaoRepository {
    void salvar(Publicacao publicacao);

    Publicacao obter(PublicacaoId id);

    List<Publicacao> listarPorEvento(EventoId id);
}
