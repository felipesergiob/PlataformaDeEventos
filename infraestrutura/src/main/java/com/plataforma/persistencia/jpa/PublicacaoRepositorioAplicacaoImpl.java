package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.publicacao.PublicacaoResumo;
import com.plataforma.aplicacao.publicacao.PublicacaoResumoImpl;
import com.plataforma.aplicacao.publicacao.PublicacaoRepositorioAplicacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicacaoRepositorioAplicacaoImpl implements PublicacaoRepositorioAplicacao {
    
    private final PublicacaoJpaRepository publicacaoRepository;
    private final UsuarioJpaRepository usuarioRepository;
    private final EventoJpaRepository eventoRepository;

    private PublicacaoJpa toJpa(PublicacaoResumo publicacao) {
        var jpa = new PublicacaoJpa();
        if (publicacao.getId() != null) {
            jpa.setId(Integer.parseInt(publicacao.getId()));
        }
        jpa.setTitulo(publicacao.getTitulo());
        jpa.setConteudo(publicacao.getConteudo());
        jpa.setFotos(publicacao.getFotos());
        jpa.setDataCriacao(publicacao.getDataCriacao());

        var usuario = usuarioRepository.findById(Integer.parseInt(publicacao.getUsuarioId()))
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        jpa.setUsuario(usuario);

        var evento = eventoRepository.findById(Integer.parseInt(publicacao.getEventoId()))
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        jpa.setEvento(evento);

        return jpa;
    }

    private PublicacaoResumoImpl toResumo(PublicacaoJpa jpa) {
        var resumo = new PublicacaoResumoImpl();
        resumo.setId(String.valueOf(jpa.getId()));
        resumo.setTitulo(jpa.getTitulo());
        resumo.setConteudo(jpa.getConteudo());
        resumo.setFotos(jpa.getFotos());
        resumo.setDataCriacao(jpa.getDataCriacao());
        resumo.setUsuarioId(String.valueOf(jpa.getUsuario().getId()));
        resumo.setEventoId(String.valueOf(jpa.getEvento().getId()));
        return resumo;
    }

    @Override
    public void salvar(PublicacaoResumo publicacao) {
        var publicacaoJpa = toJpa(publicacao);
        publicacaoRepository.save(publicacaoJpa);
    }

    @Override
    public Optional<PublicacaoResumo> buscarPorId(Integer id) {
        return publicacaoRepository.findById(id)
            .map(this::toResumo);
    }

    @Override
    public List<PublicacaoResumo> listarPorEvento(Integer eventoId) {
        return publicacaoRepository.findByEventoIdOrderByDataCriacaoDesc(eventoId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<PublicacaoResumo> listarPorUsuario(Integer usuarioId) {
        return publicacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }
} 