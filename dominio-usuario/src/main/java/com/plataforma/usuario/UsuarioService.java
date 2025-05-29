package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;

import static org.apache.commons.lang3.Validate.notNull;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

   public UsuarioService(UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
    this.usuarioRepository = usuarioRepository;
    this.eventoRepository = eventoRepository;
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
    eventoRepository.listarPorOrganizador(idSeguido);
   }

   public List<Evento> visualizarCalendario(UsuarioId id) { //historia 2 //feito
    notNull(id, "O id do usuário não pode ser nulo");

    var eventosCalendario = Stream.concat(
        eventoRepository.listarSalvos(id).stream(),
        eventoRepository.listarConfirmados(id).stream()
    ).collect(Collectors.toList());

    return eventosCalendario;
   }
}
