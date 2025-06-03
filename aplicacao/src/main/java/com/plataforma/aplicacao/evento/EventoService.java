package com.plataforma.aplicacao.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.usuario.Usuario;
import com.plataforma.usuario.UsuarioRepository;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Evento criarEvento(Evento evento, Long organizadorId) {
        UsuarioId usuarioId = UsuarioId.de(organizadorId.intValue());
        Usuario organizador = usuarioRepository.obter(usuarioId);
        if (organizador == null) {
            throw new RuntimeException("Organizador não encontrado");
        }
        
        evento.setOrganizador(usuarioId);
        eventoRepository.salvar(evento);
        return evento;
    }

    @Transactional
    public Evento atualizarEvento(Long id, Evento eventoAtualizado) {
        EventoId eventoId = EventoId.de(id.intValue());
        Evento evento = eventoRepository.obter(eventoId);
        if (evento == null) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        evento.setNome(eventoAtualizado.getNome());
        evento.setDescricao(eventoAtualizado.getDescricao());
        evento.setDataInicio(eventoAtualizado.getDataInicio());
        evento.setDataFim(eventoAtualizado.getDataFim());
        evento.setLocal(eventoAtualizado.getLocal());
        evento.setValor(eventoAtualizado.getValor());
        evento.setGenero(eventoAtualizado.getGenero());
        
        eventoRepository.salvar(evento);
        return evento;
    }

    @Transactional
    public void excluirEvento(Long id) {
        EventoId eventoId = EventoId.de(id.intValue());
        Evento evento = eventoRepository.obter(eventoId);
        if (evento == null) {
            throw new RuntimeException("Evento não encontrado");
        }
    }

    public List<Evento> listarEventos() {
        return eventoRepository.listarTodos();
    }

    public Optional<Evento> buscarEventoPorId(Long id) {
        EventoId eventoId = EventoId.de(id.intValue());
        Evento evento = eventoRepository.obter(eventoId);
        return Optional.ofNullable(evento);
    }

    public List<Evento> buscarEventosPorOrganizador(Long organizadorId) {
        UsuarioId usuarioId = UsuarioId.de(organizadorId.intValue());
        return eventoRepository.listarPorOrganizador(usuarioId);
    }

    @Transactional
    public void inscreverParticipante(Long eventoId, Long participanteId) {
        EventoId eventoIdObj = EventoId.de(eventoId.intValue());
        UsuarioId participanteIdObj = UsuarioId.de(participanteId.intValue());
        
        Evento evento = eventoRepository.obter(eventoIdObj);
        if (evento == null) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        Usuario participante = usuarioRepository.obter(participanteIdObj);
        if (participante == null) {
            throw new RuntimeException("Participante não encontrado");
        }
        
        if (eventoRepository.usuarioParticipouDoEvento(participanteIdObj, eventoIdObj)) {
            throw new RuntimeException("Participante já inscrito no evento");
        }
        
    }

    @Transactional
    public void cancelarInscricao(Long eventoId, Long participanteId) {
        EventoId eventoIdObj = EventoId.de(eventoId.intValue());
        UsuarioId participanteIdObj = UsuarioId.de(participanteId.intValue());
        
        Evento evento = eventoRepository.obter(eventoIdObj);
        if (evento == null) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        Usuario participante = usuarioRepository.obter(participanteIdObj);
        if (participante == null) {
            throw new RuntimeException("Participante não encontrado");
        }
        
        if (!eventoRepository.usuarioParticipouDoEvento(participanteIdObj, eventoIdObj)) {
            throw new RuntimeException("Participante não está inscrito no evento");
        }
        
    }

    public List<Evento> buscarEventosPorGenero(String genero) {
        return eventoRepository.listarPorFiltros(genero, null, null, null, null);
    }

    public List<Evento> buscarEventosEmDestaque() {
        return eventoRepository.listarTodos();
    }
} 