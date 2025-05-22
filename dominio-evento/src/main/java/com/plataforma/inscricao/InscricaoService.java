package com.plataforma.inscricao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoService eventoService) {
        notNull(inscricaoRepository, "O repositório de inscrições não pode ser nulo");
        notNull(eventoService, "O serviço de eventos não pode ser nulo");
        this.inscricaoRepository = inscricaoRepository;
        this.eventoService = eventoService;
    }

    @Transactional
    public void inscrever(EventoId eventoId, UsuarioId usuarioId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        notNull(usuarioId, "O ID do usuário não pode ser nulo");

        if (inscricaoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId)) {
            throw new IllegalStateException("Usuário já está inscrito neste evento");
        }

        Evento evento = eventoService.obter(eventoId);
        if (!evento.temVagasDisponiveis()) {
            throw new IllegalStateException("Evento não possui vagas disponíveis");
        }

        Inscricao inscricao = new Inscricao(
            InscricaoId.novo(),
            usuarioId,
            eventoId,
            LocalDateTime.now(),
            null,
            null
        );
        
        inscricaoRepository.save(inscricao);
        eventoService.incrementarInscritos(eventoId);
    }

    @Transactional
    public void confirmar(InscricaoId id) {
        notNull(id, "O ID da inscrição não pode ser nulo");
        Inscricao inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Inscrição não encontrada"));
        inscricao.confirmar();
        inscricaoRepository.save(inscricao);
    }

    @Transactional
    public void cancelar(InscricaoId id) {
        notNull(id, "O ID da inscrição não pode ser nulo");
        Inscricao inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Inscrição não encontrada"));
        inscricao.cancelar();
        inscricaoRepository.save(inscricao);
        eventoService.decrementarInscritos(inscricao.getEventoId());
    }

    public List<Evento> listarEventosConfirmados(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return inscricaoRepository.findByUsuarioId(usuarioId).stream()
            .filter(Inscricao::estaConfirmada)
            .map(inscricao -> eventoService.obter(inscricao.getEventoId()))
            .collect(Collectors.toList());
    }

    public List<Inscricao> listarInscricoesPorUsuario(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return inscricaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Inscricao> listarInscricoesPorEvento(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return inscricaoRepository.findByEventoId(eventoId);
    }

    public Optional<Inscricao> obter(UsuarioId usuarioId, EventoId eventoId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return inscricaoRepository.findByUsuarioIdAndEventoId(usuarioId, eventoId);
    }
} 