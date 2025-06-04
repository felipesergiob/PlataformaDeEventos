package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.participante.ParticipanteResumo;
import com.plataforma.aplicacao.participante.ParticipanteResumoImpl;
import com.plataforma.aplicacao.participante.ParticipanteRepositorioAplicacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ParticipanteRepositorioAplicacaoImpl implements ParticipanteRepositorioAplicacao {
    
    private final ParticipanteJpaRepository participanteRepository;

    private ParticipanteResumoImpl toResumo(ParticipanteJpa jpa) {
        var resumo = new ParticipanteResumoImpl();
        resumo.setId(String.valueOf(jpa.getId()));
        resumo.setEventoId(String.valueOf(jpa.getEvento().getId()));
        resumo.setUsuarioId(String.valueOf(jpa.getUsuario().getId()));
        resumo.setStatus(jpa.getStatus());
        resumo.setDataCriacao(jpa.getDataCriacao());
        return resumo;
    }

    @Override
    public Optional<ParticipanteResumo> buscarParticipacao(Integer eventoId, Integer usuarioId) {
        return participanteRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
            .map(this::toResumo);
    }

    @Override
    public List<ParticipanteResumo> listarPorEvento(Integer eventoId) {
        return participanteRepository.findByEventoIdOrderByDataCriacaoDesc(eventoId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<ParticipanteResumo> listarPorUsuario(Integer usuarioId) {
        return participanteRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }
} 