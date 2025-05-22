package com.plataforma.avaliacao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

@Service
public class AvaliacaoService {
    
    private final AvaliacaoRepository avaliacaoRepository;
    
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        notNull(avaliacaoRepository, "O repositório de avaliações não pode ser nulo");
        this.avaliacaoRepository = avaliacaoRepository;
    }
    
    @Transactional
    public Avaliacao avaliar(UsuarioId usuarioId, EventoId eventoId, Integer nota, String comentario) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(nota, "A nota não pode ser nula");
        notNull(comentario, "O comentário não pode ser nulo");
        
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        
        // Verifica se já existe uma avaliação ativa
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository
            .findByUsuarioIdAndEventoId(usuarioId, eventoId);
            
        if (avaliacaoExistente.isPresent() && avaliacaoExistente.get().estaAtiva()) {
            throw new IllegalStateException("Usuário já avaliou este evento");
        }
        
        LocalDateTime agora = LocalDateTime.now();
        Avaliacao avaliacao = new Avaliacao(
            AvaliacaoId.novo(),
            usuarioId,
            eventoId,
            "Usuário", // TODO: Buscar nome do usuário
            nota,
            comentario,
            agora,
            agora,
            true,
            false,
            null
        );
        
        return avaliacaoRepository.save(avaliacao);
    }
    
    @Transactional
    public Avaliacao desativar(AvaliacaoId avaliacaoId) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        
        Avaliacao avaliacao = avaliacaoRepository
            .findById(avaliacaoId)
            .orElseThrow(() -> new IllegalStateException("Avaliação não encontrada"));
            
        if (!avaliacao.estaAtiva()) {
            throw new IllegalStateException("Avaliação já está desativada");
        }
        
        LocalDateTime agora = LocalDateTime.now();
        Avaliacao avaliacaoAtualizada = new Avaliacao(
            avaliacao.getId(),
            avaliacao.getUsuarioId(),
            avaliacao.getEventoId(),
            avaliacao.getNomeUsuario(),
            avaliacao.getNota(),
            avaliacao.getComentario(),
            avaliacao.getDataAvaliacao(),
            agora,
            false,
            avaliacao.isComentarioModerado(),
            avaliacao.getDataModeracao()
        );
        
        return avaliacaoRepository.save(avaliacaoAtualizada);
    }
    
    @Transactional
    public Avaliacao ativar(AvaliacaoId avaliacaoId) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        
        Avaliacao avaliacao = avaliacaoRepository
            .findById(avaliacaoId)
            .orElseThrow(() -> new IllegalStateException("Avaliação não encontrada"));
            
        if (avaliacao.estaAtiva()) {
            throw new IllegalStateException("Avaliação já está ativa");
        }
        
        LocalDateTime agora = LocalDateTime.now();
        Avaliacao avaliacaoAtualizada = new Avaliacao(
            avaliacao.getId(),
            avaliacao.getUsuarioId(),
            avaliacao.getEventoId(),
            avaliacao.getNomeUsuario(),
            avaliacao.getNota(),
            avaliacao.getComentario(),
            avaliacao.getDataAvaliacao(),
            agora,
            true,
            avaliacao.isComentarioModerado(),
            avaliacao.getDataModeracao()
        );
        
        return avaliacaoRepository.save(avaliacaoAtualizada);
    }
    
    @Transactional
    public Avaliacao editar(AvaliacaoId avaliacaoId, String comentario) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        notNull(comentario, "O comentário não pode ser nulo");
        
        Avaliacao avaliacao = avaliacaoRepository
            .findById(avaliacaoId)
            .orElseThrow(() -> new IllegalStateException("Avaliação não encontrada"));
            
        if (!avaliacao.estaAtiva()) {
            throw new IllegalStateException("Não é possível editar uma avaliação desativada");
        }
        
        LocalDateTime agora = LocalDateTime.now();
        Avaliacao avaliacaoAtualizada = new Avaliacao(
            avaliacao.getId(),
            avaliacao.getUsuarioId(),
            avaliacao.getEventoId(),
            avaliacao.getNomeUsuario(),
            avaliacao.getNota(),
            comentario,
            avaliacao.getDataAvaliacao(),
            agora,
            true,
            avaliacao.isComentarioModerado(),
            avaliacao.getDataModeracao()
        );
        
        return avaliacaoRepository.save(avaliacaoAtualizada);
    }
    
    @Transactional
    public Avaliacao moderar(AvaliacaoId avaliacaoId, String comentarioModerado) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        notNull(comentarioModerado, "O comentário moderado não pode ser nulo");
        
        Avaliacao avaliacao = avaliacaoRepository
            .findById(avaliacaoId)
            .orElseThrow(() -> new IllegalStateException("Avaliação não encontrada"));
            
        if (!avaliacao.estaAtiva()) {
            throw new IllegalStateException("Não é possível moderar uma avaliação desativada");
        }
        
        if (avaliacao.isComentarioModerado()) {
            throw new IllegalStateException("Avaliação já está moderada");
        }
        
        LocalDateTime agora = LocalDateTime.now();
        Avaliacao avaliacaoAtualizada = new Avaliacao(
            avaliacao.getId(),
            avaliacao.getUsuarioId(),
            avaliacao.getEventoId(),
            avaliacao.getNomeUsuario(),
            avaliacao.getNota(),
            avaliacao.getComentario(),
            avaliacao.getDataAvaliacao(),
            agora,
            true,
            true,
            agora
        );
        
        return avaliacaoRepository.save(avaliacaoAtualizada);
    }
    
    public List<Avaliacao> listarPorUsuario(UsuarioId usuarioId) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        return avaliacaoRepository.findByUsuarioId(usuarioId);
    }
    
    public List<Avaliacao> listarPorEvento(EventoId eventoId) {
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.findByEventoId(eventoId);
    }
    
    public Optional<Avaliacao> obter(UsuarioId usuarioId, EventoId eventoId) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        return avaliacaoRepository.findByUsuarioIdAndEventoId(usuarioId, eventoId);
    }
    
    public boolean existe(AvaliacaoId avaliacaoId) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        return avaliacaoRepository.existsById(avaliacaoId);
    }

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.listarTodos();
    }

    public List<Avaliacao> listarAtivas() {
        return avaliacaoRepository.listarAtivas();
    }

    public List<Avaliacao> listarPorNota(Integer notaMinima, Integer notaMaxima) {
        notNull(notaMinima, "A nota mínima não pode ser nula");
        notNull(notaMaxima, "A nota máxima não pode ser nula");
        isTrue(notaMinima >= 1 && notaMinima <= 5, "A nota mínima deve estar entre 1 e 5");
        isTrue(notaMaxima >= 1 && notaMaxima <= 5, "A nota máxima deve estar entre 1 e 5");
        isTrue(notaMinima <= notaMaxima, "A nota mínima deve ser menor ou igual à nota máxima");
        return avaliacaoRepository.listarPorNota(notaMinima, notaMaxima);
    }

    public void excluir(AvaliacaoId id) {
        notNull(id, "O ID da avaliação não pode ser nulo");
        if (!avaliacaoRepository.existe(id)) {
            throw new IllegalArgumentException("Avaliação não encontrada");
        }
        avaliacaoRepository.excluir(id);
    }

    public double obterMediaNotas(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return avaliacaoRepository.calcularMediaNotas(eventoId);
    }

    public List<Avaliacao> listarAvaliacoesPositivas(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return avaliacaoRepository.listarPorNota(4, 5);
    }

    public List<Avaliacao> listarAvaliacoesNegativas(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return avaliacaoRepository.listarPorNota(1, 2);
    }

    public List<Avaliacao> listarComentariosPendentesModeracao() {
        return avaliacaoRepository.listarTodos().stream()
            .filter(Avaliacao::comentarioPrecisaModeracao)
            .toList();
    }

    public List<Avaliacao> listarComentariosModerados() {
        return avaliacaoRepository.listarTodos().stream()
            .filter(Avaliacao::temComentarioModerado)
            .toList();
    }
} 