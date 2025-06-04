package com.plataforma.aplicacao.participante;

import java.util.List;
import java.util.Optional;

public interface ParticipanteRepositorioAplicacao {
    Optional<ParticipanteResumo> buscarParticipacao(Integer eventoId, Integer usuarioId);
    List<ParticipanteResumo> listarPorEvento(Integer eventoId);
    List<ParticipanteResumo> listarPorUsuario(Integer usuarioId);
} 