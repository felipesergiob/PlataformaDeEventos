package com.plataforma.item;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import java.util.List;
import java.time.LocalDateTime;

public interface ItemPerdidoRepository {
    void salvar(ItemPerdido item);
    ItemPerdido obter(ItemPerdidoId id);
    List<ItemPerdido> listarTodos();
    List<ItemPerdido> listarPorEvento(EventoId eventoId);
    List<ItemPerdido> listarPendentes();
    List<ItemPerdido> listarRecuperados();
    List<ItemPerdido> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<ItemPerdido> listarPorRegistrante(UsuarioId usuarioId);
    List<ItemPerdido> listarPorRecuperador(UsuarioId usuarioId);
    void excluir(ItemPerdidoId id);
    boolean existe(ItemPerdidoId id);
} 