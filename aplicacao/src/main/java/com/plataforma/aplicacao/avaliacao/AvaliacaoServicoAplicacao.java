package com.plataforma.aplicacao.avaliacao;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.plataforma.aplicacao.evento.EventoRepositorioAplicacao;
import com.plataforma.aplicacao.participante.ParticipanteRepositorioAplicacao;

@Service
@RequiredArgsConstructor
public class AvaliacaoServicoAplicacao {
    
    private final AvaliacaoRepositorioAplicacao avaliacaoRepositorio;
    private final EventoRepositorioAplicacao eventoRepositorio;
    private final ParticipanteRepositorioAplicacao participanteRepositorio;

    public void avaliarEvento(CriarAvaliacaoRequest request) {
        // Verifica se o evento existe
        var evento = eventoRepositorio.buscarPorId(request.getEventoId())
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        // Verifica se o evento já aconteceu
        if (evento.getDataFim().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("O evento ainda não terminou");
        }

        // Verifica se o usuário confirmou presença
        var participacao = participanteRepositorio.buscarParticipacao(request.getEventoId(), request.getUsuarioId())
            .orElseThrow(() -> new IllegalStateException("Usuário não confirmou presença neste evento"));

        if (!participacao.getStatus().equals("CONFIRMADO")) {
            throw new IllegalStateException("Usuário não confirmou presença neste evento");
        }

        // Verifica se o usuário já avaliou este evento
        if (avaliacaoRepositorio.buscarPorEventoEUsuario(request.getEventoId(), request.getUsuarioId()).isPresent()) {
            throw new IllegalStateException("Usuário já avaliou este evento");
        }

        // Cria a avaliação
        var avaliacao = new AvaliacaoResumoImpl();
        avaliacao.setEventoId(String.valueOf(request.getEventoId()));
        avaliacao.setUsuarioId(String.valueOf(request.getUsuarioId()));
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setDataCriacao(LocalDateTime.now());

        avaliacaoRepositorio.salvar(avaliacao);
    }

    public List<AvaliacaoResumo> listarAvaliacoesPorEvento(Integer eventoId) {
        return avaliacaoRepositorio.listarPorEvento(eventoId);
    }

    public List<AvaliacaoResumo> listarAvaliacoesPorUsuario(Integer usuarioId) {
        return avaliacaoRepositorio.listarPorUsuario(usuarioId);
    }

    public List<AvaliacaoResumo> listarTodasAvaliacoes() {
        return avaliacaoRepositorio.listarTodas();
    }
} 