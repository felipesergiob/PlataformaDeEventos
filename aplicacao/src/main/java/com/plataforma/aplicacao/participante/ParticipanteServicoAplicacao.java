package com.plataforma.aplicacao.participante;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteServicoAplicacao {

    private final ParticipanteRepositorioAplicacao participanteRepositorio;

    @Transactional
    public ParticipanteResumo criarParticipacao(Integer eventoId, Integer usuarioId, String status) {
        var participacao = participanteRepositorio.buscarParticipacao(eventoId, usuarioId);
        if (participacao.isPresent()) {
            throw new RuntimeException("Participação já existe para este usuário neste evento");
        }
        return participanteRepositorio.criarParticipacao(eventoId, usuarioId, status);
    }

    @Transactional
    public void atualizarStatus(Integer eventoId, Integer usuarioId, String status) {
        var participacao = participanteRepositorio.buscarParticipacao(eventoId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        participanteRepositorio.atualizarStatus(eventoId, usuarioId, status);
    }

    public List<ParticipanteResumo> listarPorEvento(Integer eventoId) {
        return participanteRepositorio.listarPorEvento(eventoId);
    }

    public List<ParticipanteResumo> listarPorUsuario(Integer usuarioId) {
        return participanteRepositorio.listarPorUsuario(usuarioId);
    }

    public ParticipanteResumo buscarParticipacao(Integer eventoId, Integer usuarioId) {
        return participanteRepositorio.buscarParticipacao(eventoId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
    }
} 