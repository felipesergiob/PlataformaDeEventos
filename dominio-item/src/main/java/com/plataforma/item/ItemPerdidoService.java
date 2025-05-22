package com.plataforma.item;

import com.plataforma.evento.EventoId;
import com.plataforma.usuario.UsuarioId;
import java.util.List;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

public class ItemPerdidoService {
    private final ItemPerdidoRepository itemPerdidoRepository;

    public ItemPerdidoService(ItemPerdidoRepository itemPerdidoRepository) {
        notNull(itemPerdidoRepository, "O repositório de itens perdidos não pode ser nulo");
        this.itemPerdidoRepository = itemPerdidoRepository;
    }

    public void registrar(ItemPerdido item) {
        notNull(item, "O item não pode ser nulo");
        itemPerdidoRepository.salvar(item);
    }

    public ItemPerdido obter(ItemPerdidoId id) {
        notNull(id, "O ID do item não pode ser nulo");
        ItemPerdido item = itemPerdidoRepository.obter(id);
        if (item == null) {
            throw new IllegalArgumentException("Item não encontrado");
        }
        return item;
    }

    public void marcarComoRecuperado(ItemPerdidoId id, UsuarioId recuperadoPor) {
        notNull(id, "O ID do item não pode ser nulo");
        notNull(recuperadoPor, "O usuário que recuperou não pode ser nulo");
        
        ItemPerdido item = obter(id);
        item.marcarComoRecuperado(recuperadoPor);
        itemPerdidoRepository.salvar(item);
    }

    public void arquivar(ItemPerdidoId id) {
        notNull(id, "O ID do item não pode ser nulo");
        
        ItemPerdido item = obter(id);
        item.arquivar();
        itemPerdidoRepository.salvar(item);
    }

    public List<ItemPerdido> listarPorEvento(EventoId eventoId) {
        notNull(eventoId, "O ID do evento não pode ser nulo");
        return itemPerdidoRepository.listarPorEvento(eventoId);
    }

    public List<ItemPerdido> listarPendentes() {
        return itemPerdidoRepository.listarPendentes();
    }

    public List<ItemPerdido> listarRecuperados() {
        return itemPerdidoRepository.listarRecuperados();
    }

    public List<ItemPerdido> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        notNull(inicio, "A data de início não pode ser nula");
        notNull(fim, "A data de fim não pode ser nula");
        isTrue(inicio.isBefore(fim), "A data de início deve ser anterior à data de fim");
        return itemPerdidoRepository.listarPorPeriodo(inicio, fim);
    }

    public List<ItemPerdido> listarPorRegistrante(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return itemPerdidoRepository.listarPorRegistrante(usuarioId);
    }

    public List<ItemPerdido> listarPorRecuperador(UsuarioId usuarioId) {
        notNull(usuarioId, "O ID do usuário não pode ser nulo");
        return itemPerdidoRepository.listarPorRecuperador(usuarioId);
    }

    public void excluir(ItemPerdidoId id) {
        notNull(id, "O ID do item não pode ser nulo");
        if (!itemPerdidoRepository.existe(id)) {
            throw new IllegalArgumentException("Item não encontrado");
        }
        itemPerdidoRepository.excluir(id);
    }
} 