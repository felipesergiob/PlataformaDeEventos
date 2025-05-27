package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;

import static org.apache.commons.lang3.Validate.notNull;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final Evento evento;

   public UsuarioService(UsuarioRepository usuarioRepository, Evento evento) {
    this.usuarioRepository = usuarioRepository;
    this.evento = evento;
   }

   public void obter(UsuarioId id) {
    notNull(id, "O id do usuário não pode ser nulo");
    usuarioRepository.obter(id);
   }

   public void salvar(Usuario usuario) {
    notNull(usuario, "O usuário não pode ser nulo");
    usuarioRepository.salvar(usuario);
   }

   public void seguirUsuarioEListarEventos(UsuarioId id, UsuarioId idSeguido) { //historia 7
    notNull(id, "O id do usuário não pode ser nulo");
    notNull(idSeguido, "O id do usuário a ser seguido não pode ser nulo");

    usuarioRepository.seguirUsuario(id, idSeguido);
    evento.listarPorOrganizador(idSeguido);
   }

   public List<Evento> visualizarCalendario(UsuarioId id) { //historia 2
    notNull(id, "O id do usuário não pode ser nulo");

    var eventosCalendario = Stream.concat(
        evento.listarSalvos(id).stream(),
        evento.listarConfirmados(id).stream()
    ).collect(Collectors.toList());

    return eventosCalendario;
   }
}
