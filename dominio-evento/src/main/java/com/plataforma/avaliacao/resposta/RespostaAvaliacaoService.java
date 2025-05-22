package com.plataforma.avaliacao.resposta;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.AvaliacaoId;
import com.plataforma.avaliacao.AvaliacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class RespostaAvaliacaoService {
    
    private final RespostaAvaliacaoRepository respostaRepository;
    private final AvaliacaoService avaliacaoService;
    
    public RespostaAvaliacaoService(RespostaAvaliacaoRepository respostaRepository,
                                  AvaliacaoService avaliacaoService) {
        this.respostaRepository = respostaRepository;
        this.avaliacaoService = avaliacaoService;
    }
    
    @Transactional
    public RespostaAvaliacao responder(UsuarioId usuarioId, AvaliacaoId avaliacaoId, String comentario) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        notNull(comentario, "O comentário não pode ser nulo");
        
        if (!avaliacaoService.existe(avaliacaoId)) {
            throw new IllegalStateException("Avaliação não encontrada");
        }
        
        Optional<RespostaAvaliacao> respostaExistente = respostaRepository
            .findByUsuarioIdAndAvaliacaoId(usuarioId, avaliacaoId);
            
        if (respostaExistente.isPresent() && respostaExistente.get().estaAtiva()) {
            throw new IllegalStateException("Usuário já respondeu esta avaliação");
        }
        
        RespostaAvaliacao resposta = new RespostaAvaliacao(
            RespostaAvaliacaoId.novo(),
            usuarioId,
            avaliacaoId,
            LocalDateTime.now(),
            comentario
        );
        
        return respostaRepository.save(resposta);
    }
    
    @Transactional
    public RespostaAvaliacao moderar(RespostaAvaliacaoId respostaId, String comentarioModerado) {
        notNull(respostaId, "O id da resposta não pode ser nulo");
        notNull(comentarioModerado, "O comentário moderado não pode ser nulo");
        
        RespostaAvaliacao resposta = respostaRepository
            .findById(respostaId)
            .orElseThrow(() -> new IllegalStateException("Resposta não encontrada"));
            
        resposta.moderarComentario(comentarioModerado);
        return respostaRepository.save(resposta);
    }
    
    public List<RespostaAvaliacao> listarPorUsuario(UsuarioId usuarioId) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        return respostaRepository.findByUsuarioId(usuarioId);
    }
    
    public List<RespostaAvaliacao> listarPorAvaliacao(AvaliacaoId avaliacaoId) {
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        return respostaRepository.findByAvaliacaoId(avaliacaoId);
    }
    
    public Optional<RespostaAvaliacao> obter(UsuarioId usuarioId, AvaliacaoId avaliacaoId) {
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        return respostaRepository.findByUsuarioIdAndAvaliacaoId(usuarioId, avaliacaoId);
    }
} 