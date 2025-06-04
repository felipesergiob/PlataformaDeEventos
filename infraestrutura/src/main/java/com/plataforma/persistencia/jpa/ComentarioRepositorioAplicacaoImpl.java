package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.comentario.ComentarioResumo;
import com.plataforma.aplicacao.comentario.ComentarioResumoImpl;
import com.plataforma.aplicacao.comentario.ComentarioRepositorioAplicacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComentarioRepositorioAplicacaoImpl implements ComentarioRepositorioAplicacao {
    
    private final ComentarioJpaRepository comentarioRepository;
    private final UsuarioJpaRepository usuarioRepository;
    private final EventoJpaRepository eventoRepository;

    private ComentarioJpa toJpa(ComentarioResumo comentario) {
        var jpa = new ComentarioJpa();
        if (comentario.getId() != null) {
            jpa.setId(Integer.parseInt(comentario.getId()));
        }
        jpa.setComentario(comentario.getComentario());
        jpa.setDataCriacao(comentario.getDataCriacao());

        if (comentario.getComentarioPaiId() != null) {
            var comentarioPai = comentarioRepository.findById(Integer.parseInt(comentario.getComentarioPaiId()))
                .orElseThrow(() -> new IllegalArgumentException("Comentário pai não encontrado"));
            jpa.setComentarioPai(comentarioPai);
        }

        var usuario = usuarioRepository.findById(Integer.parseInt(comentario.getUsuarioId()))
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        jpa.setUsuario(usuario);

        var evento = eventoRepository.findById(Integer.parseInt(comentario.getEventoId()))
            .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        jpa.setEvento(evento);

        return jpa;
    }

    private ComentarioResumoImpl toResumo(ComentarioJpa jpa) {
        var resumo = new ComentarioResumoImpl();
        resumo.setId(String.valueOf(jpa.getId()));
        resumo.setComentario(jpa.getComentario());
        resumo.setDataCriacao(jpa.getDataCriacao());
        if (jpa.getComentarioPai() != null) {
            resumo.setComentarioPaiId(String.valueOf(jpa.getComentarioPai().getId()));
        }
        resumo.setUsuarioId(String.valueOf(jpa.getUsuario().getId()));
        resumo.setEventoId(String.valueOf(jpa.getEvento().getId()));
        return resumo;
    }

    @Override
    public void salvar(ComentarioResumo comentario) {
        var comentarioJpa = toJpa(comentario);
        comentarioRepository.save(comentarioJpa);
    }

    @Override
    public Optional<ComentarioResumo> buscarPorId(Integer id) {
        return comentarioRepository.findById(id)
            .map(this::toResumo);
    }

    @Override
    public List<ComentarioResumo> listarPorEvento(Integer eventoId) {
        return comentarioRepository.findByEventoIdOrderByDataCriacaoDesc(eventoId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<ComentarioResumo> listarPorUsuario(Integer usuarioId) {
        return comentarioRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<ComentarioResumo> listarRespostas(Integer comentarioPaiId) {
        return comentarioRepository.findByComentarioPaiIdOrderByDataCriacaoAsc(comentarioPaiId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }
} 