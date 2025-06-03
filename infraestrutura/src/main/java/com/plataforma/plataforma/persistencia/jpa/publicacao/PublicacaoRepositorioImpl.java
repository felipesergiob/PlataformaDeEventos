package com.plataforma.plataforma.persistencia.jpa.publicacao;

import com.plataforma.Publicacao.Publicacao;
import com.plataforma.Publicacao.PublicacaoRepository;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.PublicacaoId;
import com.plataforma.compartilhado.UsuarioId;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PublicacaoRepositorioImpl implements PublicacaoRepository {
    private final PublicacaoJpaRepository jpaRepository;

    public PublicacaoRepositorioImpl(PublicacaoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Publicacao publicacao) {
        PublicacaoJpa publicacaoJpa = new PublicacaoJpa();
        publicacaoJpa.setId(publicacao.getId().getValor());
        publicacaoJpa.setEventoId(publicacao.getEventoId().getValor());
        publicacaoJpa.setUsuarioId(publicacao.getAutorId().getValor());
        publicacaoJpa.setConteudo(publicacao.getConteudo());
        publicacaoJpa.setDataCriacao(publicacao.getDataCriacao());
        publicacaoJpa.setFotos(String.join(",", publicacao.getFotos()));
        
        jpaRepository.save(publicacaoJpa);
    }

    @Override
    public Publicacao obter(PublicacaoId id) {
        return jpaRepository.findById(id.getValor())
            .map(this::toPublicacao)
            .orElse(null);
    }

    @Override
    public List<Publicacao> listarPorEvento(EventoId id) {
        return jpaRepository.findByEventoId(id.getValor())
            .stream()
            .map(this::toPublicacao)
            .collect(Collectors.toList());
    }

    @Override
    public List<Publicacao> listarPorAutor(UsuarioId autorId) {
        return jpaRepository.findByUsuarioId(autorId.getValor())
            .stream()
            .map(this::toPublicacao)
            .collect(Collectors.toList());
    }

    private Publicacao toPublicacao(PublicacaoJpa jpa) {
        return new Publicacao(
            new PublicacaoId(jpa.getId()),
            new UsuarioId(jpa.getUsuarioId()),
            new EventoId(jpa.getEventoId()),
            jpa.getConteudo()
        );
    }
} 