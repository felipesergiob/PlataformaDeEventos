package com.plataforma.aplicacao.evento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServicoAplicacao {
    
    private final EventoRepositorioAplicacao repositorio;
    private final UsuarioRepositorioAplicacao usuarioRepositorio;

    @Transactional
    public EventoResumo criar(CriarEventoRequest request) {
        // Valida se o organizador existe
        var organizador = usuarioRepositorio.buscarPorId(request.getOrganizadorId())
            .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));

        // Valida as datas
        if (request.getDataInicio().isAfter(request.getDataFim())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }

        // Cria o evento
        var evento = new EventoResumoImpl();
        evento.setTitulo(request.getTitulo());
        evento.setDescricao(request.getDescricao());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setLocal(request.getLocal());
        evento.setGenero(request.getGenero());
        evento.setValor(request.getValor());
        evento.setImagem(request.getImagem());
        evento.setParticipantes(0);
        evento.setDataCriacao(java.time.LocalDateTime.now());
        evento.setOrganizadorId(organizador.getId());

        repositorio.salvar(evento);
        return evento;
    }

    @Transactional(readOnly = true)
    public EventoResumo buscarPorId(Integer id) {
        return repositorio.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> listarTodos() {
        return repositorio.listarTodos();
    }

    @Transactional(readOnly = true)
    public List<EventoResumo> listarPorOrganizador(Integer organizadorId) {
        return repositorio.listarPorOrganizador(organizadorId);
    }
} 