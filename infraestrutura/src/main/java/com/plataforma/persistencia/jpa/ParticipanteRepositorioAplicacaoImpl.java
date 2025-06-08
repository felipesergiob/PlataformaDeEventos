package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.plataforma.aplicacao.participante.ParticipanteRepositorioAplicacao;
import com.plataforma.aplicacao.participante.ParticipanteResumo;
import com.plataforma.aplicacao.participante.ParticipanteResumoImpl;

@Repository
@RequiredArgsConstructor
public class ParticipanteRepositorioAplicacaoImpl implements ParticipanteRepositorioAplicacao {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ParticipanteJpaRepository participanteJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final EventoJpaRepository eventoJpaRepository;

    @Override
    public Optional<ParticipanteResumo> buscarParticipacao(Integer eventoId, Integer usuarioId) {
        return participanteJpaRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
            .map(this::toParticipanteResumo);
    }

    @Override
    public List<ParticipanteResumo> listarPorEvento(Integer eventoId) {
        return participanteJpaRepository.findByEventoId(eventoId).stream()
            .map(this::toParticipanteResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<ParticipanteResumo> listarPorUsuario(Integer usuarioId) {
        return participanteJpaRepository.findByUsuarioId(usuarioId).stream()
            .map(this::toParticipanteResumo)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipanteResumo criarParticipacao(Integer eventoId, Integer usuarioId, String status) {
        var evento = eventoJpaRepository.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        var usuario = usuarioJpaRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var participante = new ParticipanteJpa();
        participante.setEvento(evento);
        participante.setUsuario(usuario);
        participante.setStatus(status);
        participante.setDataCriacao(LocalDateTime.parse(LocalDateTime.now().format(DATE_FORMATTER), DATE_FORMATTER));

        var participanteSalvo = participanteJpaRepository.save(participante);
        return toParticipanteResumo(participanteSalvo);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer eventoId, Integer usuarioId, String status) {
        var participante = participanteJpaRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        participante.setStatus(status);
        participanteJpaRepository.save(participante);
    }

    private ParticipanteResumo toParticipanteResumo(ParticipanteJpa participante) {
        var resumo = new ParticipanteResumoImpl();
        resumo.setId(String.valueOf(participante.getId()));
        resumo.setEventoId(String.valueOf(participante.getEvento().getId()));
        resumo.setUsuarioId(String.valueOf(participante.getUsuario().getId()));
        resumo.setStatus(participante.getStatus());
        return resumo;
    }
} 