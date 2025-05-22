package com.plataforma.avaliacao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, AvaliacaoId> {
    List<Avaliacao> findByUsuarioId(UsuarioId usuarioId);
    List<Avaliacao> findByEventoId(EventoId eventoId);
    Optional<Avaliacao> findByUsuarioIdAndEventoId(UsuarioId usuarioId, EventoId eventoId);
    void salvar(Avaliacao avaliacao);
    Avaliacao obter(AvaliacaoId id);
    List<Avaliacao> listarTodos();
    List<Avaliacao> listarAtivas();
    List<Avaliacao> listarPorNota(Integer notaMinima, Integer notaMaxima);
    void excluir(AvaliacaoId id);
    boolean existe(AvaliacaoId id);
    double calcularMediaNotas(EventoId eventoId);
    List<Avaliacao> listarComentariosPendentesModeracao();
    List<Avaliacao> listarComentariosModerados();
    List<Avaliacao> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<Avaliacao> listarPorAvaliador(String nomeAvaliador);
    List<Avaliacao> listarComentariosLongos(int tamanhoMinimo);
} 