package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        notNull(usuarioRepository, "O repositório de usuários não pode ser nulo");
        notNull(eventoRepository, "O repositório de eventos não pode ser nulo");
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public Optional<Usuario> obter(UsuarioId id) {
        notNull(id, "O id do usuário não pode ser nulo");
        return usuarioRepository.obter(id);
    }

    public void registrar(Usuario usuario) {
        notNull(usuario, "O usuário não pode ser nulo");
        
        if (usuarioRepository.emailJaExiste(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        usuarioRepository.salvar(usuario);
    }

    public Optional<Usuario> login(String email, String senha) {
        notNull(email, "O email não pode ser nulo");
        notNull(senha, "A senha não pode ser nula");
        
        return usuarioRepository.login(email, senha);
    }

    public void salvar(Usuario usuario) {
        notNull(usuario, "O usuário não pode ser nulo");
        usuarioRepository.salvar(usuario);
    }

    public void seguirUsuario(UsuarioId id, UsuarioId idSeguido) {
        notNull(id, "O id do usuário não pode ser nulo");
        notNull(idSeguido, "O id do usuário a ser seguido não pode ser nulo");

        if (id.equals(idSeguido)) {
            throw new IllegalArgumentException("O usuário não pode seguir a si mesmo");
        }

        usuarioRepository.seguirUsuario(id, idSeguido);
    }

    public List<Evento> seguirUsuarioEListarEventos(UsuarioId id, UsuarioId idSeguido) {
        seguirUsuario(id, idSeguido);
        return eventoRepository.listarPorOrganizador(idSeguido).stream()
            .sorted(Comparator.comparing(Evento::getDataInicio))
            .collect(Collectors.toList());
    }

    public List<Evento> visualizarCalendario(UsuarioId id) {
        notNull(id, "O id do usuário não pode ser nulo");
        
        List<Evento> eventosSalvos = eventoRepository.listarSalvos(id);
        List<Evento> eventosConfirmados = eventoRepository.listarConfirmados(id);
        
        return Stream.concat(eventosSalvos.stream(), eventosConfirmados.stream())
            .sorted(Comparator.comparing(Evento::getDataInicio))
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> perfilUsuario(UsuarioId id) {
        notNull(id, "O id do usuário não pode ser nulo");
        
        return eventoRepository.listarPorOrganizador(id).stream()
            .filter(evento -> evento.getDataFim().isBefore(LocalDateTime.now()))
            .map(evento -> {
                Map<String, Object> eventoMap = new HashMap<>();
                eventoMap.put("nome", evento.getNome());
                eventoMap.put("dataInicio", evento.getDataInicio());
                eventoMap.put("dataFim", evento.getDataFim());
                return eventoMap;
            })
            .collect(Collectors.toList());
    }
}
