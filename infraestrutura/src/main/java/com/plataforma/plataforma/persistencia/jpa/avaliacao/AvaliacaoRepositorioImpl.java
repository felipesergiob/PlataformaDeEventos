package com.plataforma.plataforma.persistencia.jpa.avaliacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.plataforma.dominio.avaliacao.Avaliacao;
import com.plataforma.dominio.avaliacao.AvaliacaoId;
import com.plataforma.dominio.avaliacao.AvaliacaoRepositorio;

@Repository
public class AvaliacaoRepositorioImpl implements AvaliacaoRepositorio {
    @Autowired
    private AvaliacaoJpaRepository repositorio;

    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void salvar(Avaliacao avaliacao) {
        var avaliacaoJpa = mapeador.map(avaliacao, AvaliacaoJpa.class);
        repositorio.save(avaliacaoJpa);
    }

    @Override
    public Avaliacao obter(AvaliacaoId id) {
        var avaliacaoJpa = repositorio.findById(id.getId()).get();
        return mapeador.map(avaliacaoJpa, Avaliacao.class);
    }
} 