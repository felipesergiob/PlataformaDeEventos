package com.plataforma.apresentacao.usuario;

import com.plataforma.dominio.usuario.Usuario;
import com.plataforma.apresentacao.evento.EventoMapper;
import com.plataforma.apresentacao.evento.EventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    @Autowired
    private EventoMapper eventoMapper;

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        
        dto.setEventosParticipados(usuario.getEventosParticipados().stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList()));
        
        dto.setEventosOrganizados(usuario.getEventosOrganizados().stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList()));
        
        dto.setTotalEventosParticipados(usuario.getEventosParticipados().size());
        dto.setTotalEventosOrganizados(usuario.getEventosOrganizados().size());
        
        return dto;
    }

    public Usuario toDomain(UsuarioCriacaoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }

    public Usuario toDomain(LoginDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        return usuario;
    }
} 