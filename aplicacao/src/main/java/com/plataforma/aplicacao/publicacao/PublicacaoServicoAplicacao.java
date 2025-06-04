package com.plataforma.aplicacao.publicacao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataforma.aplicacao.evento.EventoRepositorioAplicacao;
import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacaoServicoAplicacao {
    
    private final PublicacaoRepositorioAplicacao repositorio;
    private final EventoRepositorioAplicacao eventoRepositorio;
    private final UsuarioRepositorioAplicacao usuarioRepositorio;

    @Transactional
    public PublicacaoResumo criar(CriarPublicacaoRequest request) {
        // Valida se o usuário existe
        var usuario = usuarioRepositorio.buscarPorId(request.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Valida se o evento existe
        var evento = eventoRepositorio.buscarPorId(request.getEventoId())
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        // Cria a publicação
        var publicacao = new PublicacaoResumoImpl();
        publicacao.setConteudo(request.getConteudo());
        publicacao.setFotos(request.getFotos());
        publicacao.setDataCriacao(java.time.LocalDateTime.now());
        publicacao.setUsuarioId(request.getUsuarioId().toString());
        publicacao.setEventoId(request.getEventoId().toString());

        repositorio.salvar(publicacao);
        return publicacao;
    }

    @Transactional(readOnly = true)
    public PublicacaoResumo buscarPorId(Integer id) {
        return repositorio.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<PublicacaoResumo> listarPorEvento(Integer eventoId) {
        // Valida se o evento existe
        eventoRepositorio.buscarPorId(eventoId)
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        return repositorio.listarPorEvento(eventoId);
    }

    @Transactional(readOnly = true)
    public List<PublicacaoResumo> listarPorUsuario(Integer usuarioId) {
        // Valida se o usuário existe
        usuarioRepositorio.buscarPorId(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return repositorio.listarPorUsuario(usuarioId);
    }
} 