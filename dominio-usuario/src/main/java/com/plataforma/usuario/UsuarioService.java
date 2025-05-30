package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.avaliacao.AvaliacaoRepository;
import com.plataforma.avaliacao.Avaliacao;
import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository,
            AvaliacaoRepository avaliacaoRepository) {
        notNull(usuarioRepository, "O repositório de usuários não pode ser nulo");
        notNull(eventoRepository, "O repositório de eventos não pode ser nulo");
        notNull(avaliacaoRepository, "O repositório de avaliações não pode ser nulo");

        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public UsuarioService(UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.avaliacaoRepository = null;
    }

    public void obter(UsuarioId id) {
        notNull(id, "O id do usuário não pode ser nulo");
        usuarioRepository.obter(id);
    }

    public void salvar(Usuario usuario) {
        notNull(usuario, "O usuário não pode ser nulo");
        usuarioRepository.salvar(usuario);
    }

    public void seguirUsuarioEListarEventos(UsuarioId id, UsuarioId idSeguido) { // historia 7 //feito
        notNull(id, "O id do usuário não pode ser nulo");
        notNull(idSeguido, "O id do usuário a ser seguido não pode ser nulo");

        if (id.equals(idSeguido)) {
            throw new IllegalArgumentException("O usuário não pode seguir a si mesmo");
        }

        usuarioRepository.seguirUsuario(id, idSeguido);
        eventoRepository.listarPorOrganizador(idSeguido);
    }

    public List<Evento> visualizarCalendario(UsuarioId id) { // historia 2 //feito
        notNull(id, "O id do usuário não pode ser nulo");

        var eventosCalendario = Stream.concat(
                eventoRepository.listarSalvos(id).stream(),
                eventoRepository.listarConfirmados(id).stream()).collect(Collectors.toList());

        return eventosCalendario;
    }

    public List<Map<String, Object>> perfilUsuario(UsuarioId id) { //historia 6
        notNull(id, "O id do usuário não pode ser nulo");

        List<Evento> eventos = eventoRepository.listarPorOrganizador(id);

        List<Evento> eventosPassados = eventos.stream()
                .filter(evento -> evento.getDataFim().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        return eventosPassados.stream().map(evento -> {
            Map<String, Object> infoEvento = new HashMap<>();

            infoEvento.put("id", evento.getId());
            infoEvento.put("nome", evento.getNome());
            infoEvento.put("dataInicio", evento.getDataInicio());
            infoEvento.put("dataFim", evento.getDataFim());

            List<Avaliacao> avaliacoes = avaliacaoRepository.listarPorEvento(evento.getId());

            double mediaNotas = avaliacoes.stream()
                    .mapToInt(Avaliacao::getNota)
                    .average()
                    .orElse(0.0);
            mediaNotas = Math.round(mediaNotas * 100.0) / 100.0;
            infoEvento.put("mediaNotas", mediaNotas);

            List<String> comentarios = avaliacoes.stream()
                    .map(Avaliacao::getComentario)
                    .collect(Collectors.toList());
            infoEvento.put("comentarios", comentarios);

            return infoEvento;
        }).collect(Collectors.toList());
    }
}
