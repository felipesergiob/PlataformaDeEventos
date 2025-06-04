package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.evento.EventoResumo;
import com.plataforma.aplicacao.evento.EventoResumoImpl;
import com.plataforma.aplicacao.evento.EventoRepositorioAplicacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventoRepositorioAplicacaoImpl implements EventoRepositorioAplicacao {
    
    private final EventoJpaRepository eventoRepository;
    private final UsuarioJpaRepository usuarioRepository;

    private EventoJpa toJpa(EventoResumo evento) {
        var jpa = new EventoJpa();
        if (evento.getId() != null) {
            jpa.setId(Integer.parseInt(evento.getId()));
        }
        jpa.setTitulo(evento.getTitulo());
        jpa.setDescricao(evento.getDescricao());
        jpa.setDataInicio(evento.getDataInicio());
        jpa.setDataFim(evento.getDataFim());
        jpa.setLocal(evento.getLocal());
        jpa.setGenero(evento.getGenero());
        jpa.setValor(evento.getValor());
        jpa.setImagem(evento.getImagem());
        jpa.setParticipantes(evento.getParticipantes());
        jpa.setDataCriacao(evento.getDataCriacao());

        // Busca o organizador
        var organizador = usuarioRepository.findById(Integer.parseInt(evento.getOrganizadorId()))
            .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));
        jpa.setOrganizador(organizador);

        return jpa;
    }

    private EventoResumoImpl toResumo(EventoJpa jpa) {
        var resumo = new EventoResumoImpl();
        resumo.setId(String.valueOf(jpa.getId()));
        resumo.setTitulo(jpa.getTitulo());
        resumo.setDescricao(jpa.getDescricao());
        resumo.setDataInicio(jpa.getDataInicio());
        resumo.setDataFim(jpa.getDataFim());
        resumo.setLocal(jpa.getLocal());
        resumo.setGenero(jpa.getGenero());
        resumo.setValor(jpa.getValor());
        resumo.setImagem(jpa.getImagem());
        resumo.setParticipantes(jpa.getParticipantes());
        resumo.setDataCriacao(jpa.getDataCriacao());
        resumo.setOrganizadorId(String.valueOf(jpa.getOrganizador().getId()));
        return resumo;
    }

    @Override
    public void salvar(EventoResumo evento) {
        var eventoJpa = toJpa(evento);
        eventoRepository.save(eventoJpa);
    }

    @Override
    public Optional<EventoResumo> buscarPorId(Integer id) {
        return eventoRepository.findById(id)
            .map(this::toResumo);
    }

    @Override
    public List<EventoResumo> listarTodos() {
        return eventoRepository.findAll().stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    public List<EventoResumo> listarPorOrganizador(Integer organizadorId) {
        return eventoRepository.findByOrganizadorId(organizadorId).stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }
} 