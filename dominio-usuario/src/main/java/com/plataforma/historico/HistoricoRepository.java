package com.plataforma.historico;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import java.util.List;
import java.time.LocalDateTime;

public interface HistoricoRepository {
    void salvar(Historico historico);
    Historico obter(HistoricoId id);
    List<Historico> listarTodos();
    List<Historico> listarPorUsuario(UsuarioId usuarioId);
    List<Historico> listarPorEvento(EventoId eventoId);
    List<Historico> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<Historico> listarComAvaliacao();
    List<Historico> listarSemAvaliacao();
    void excluir(HistoricoId id);
    boolean existe(HistoricoId id);
} 