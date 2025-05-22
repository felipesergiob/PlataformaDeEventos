package com.plataforma.avaliacao.resposta;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.AvaliacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RespostaAvaliacaoRepository extends JpaRepository<RespostaAvaliacao, RespostaAvaliacaoId> {
    void salvar(RespostaAvaliacao resposta);
    RespostaAvaliacao obter(RespostaAvaliacaoId id);
    List<RespostaAvaliacao> listarTodos();
    List<RespostaAvaliacao> listarPorAvaliacao(AvaliacaoId avaliacaoId);
    List<RespostaAvaliacao> listarPorUsuario(UsuarioId usuarioId);
    List<RespostaAvaliacao> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<RespostaAvaliacao> listarComentariosPendentesModeracao();
    List<RespostaAvaliacao> listarComentariosModerados();
    void excluir(RespostaAvaliacaoId id);
    boolean existe(RespostaAvaliacaoId id);
    List<RespostaAvaliacao> findByUsuarioId(UsuarioId usuarioId);
    List<RespostaAvaliacao> findByAvaliacaoId(AvaliacaoId avaliacaoId);
    Optional<RespostaAvaliacao> findByUsuarioIdAndAvaliacaoId(UsuarioId usuarioId, AvaliacaoId avaliacaoId);
} 