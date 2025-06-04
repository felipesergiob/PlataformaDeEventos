package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.avaliacao.AvaliacaoResumo;
import com.plataforma.aplicacao.avaliacao.AvaliacaoResumoImpl;
import com.plataforma.aplicacao.avaliacao.AvaliacaoRepositorioAplicacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AvaliacaoRepositorioAplicacaoImpl implements AvaliacaoRepositorioAplicacao {
    
    private final AvaliacaoJpaRepository avaliacaoRepository;
    private final UsuarioJpaRepository usuarioRepository;
    private final EventoJpaRepository eventoRepository;

    private AvaliacaoJpa toJpa(AvaliacaoResumo avaliacao) {
        var jpa = new AvaliacaoJpa();
        if (avaliacao.getId() != null) {
            jpa.setId(Integer.parseInt(avaliacao.getId()));
        }
        jpa.setNota(avaliacao.getNota());
        jpa.setComentario(avaliacao.getComentario());
        jpa.setDataCriacao(avaliacao.getDataCriacao());

        var usuario = usuarioRepository.findById(Integer.parseInt(avaliacao.getUsuarioId()))
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        jpa.setUsuario(usuario);

        var evento = eventoRepository.findById(Integer.parseInt(avaliacao.getEventoId()))
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        jpa.setEvento(evento);

        return jpa;
    }

    private AvaliacaoResumoImpl toResumo(AvaliacaoJpa jpa) {
        var resumo = new AvaliacaoResumoImpl();
        resumo.setId(String.valueOf(jpa.getId()));
        resumo.setNota(jpa.getNota());
        resumo.setComentario(jpa.getComentario());
        resumo.setDataCriacao(jpa.getDataCriacao());
        resumo.setUsuarioId(String.valueOf(jpa.getUsuario().getId()));
        resumo.setEventoId(String.valueOf(jpa.getEvento().getId()));
        return resumo;
    }

    @Override
    public void salvar(AvaliacaoResumo avaliacao) {
        var avaliacaoJpa = toJpa(avaliacao);
        avaliacaoRepository.save(avaliacaoJpa);
    }

    @Override
    public Optional<AvaliacaoResumo> buscarPorId(Integer id) {
        return avaliacaoRepository.findById(id)
            .map(this::toResumo);
    }

    @Override
    public List<AvaliacaoResumo> listarPorEvento(Integer eventoId) {
        return avaliacaoRepository.findByEventoIdOrderByDataCriacaoDesc(eventoId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<AvaliacaoResumo> listarPorUsuario(Integer usuarioId) {
        return avaliacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AvaliacaoResumo> buscarPorEventoEUsuario(Integer eventoId, Integer usuarioId) {
        return avaliacaoRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
            .map(this::toResumo);
    }

    @Override
    public List<AvaliacaoResumo> listarTodas() {
        return avaliacaoRepository.findAll().stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }
} 