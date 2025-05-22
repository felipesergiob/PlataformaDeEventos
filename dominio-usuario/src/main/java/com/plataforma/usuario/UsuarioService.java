package com.plataforma.usuario;

import java.util.List;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        notNull(usuarioRepository, "O repositório de usuários não pode ser nulo");
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {
        notNull(usuario, "O usuário não pode ser nulo");
        if (usuarioRepository.existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        usuarioRepository.salvar(usuario);
    }

    public Usuario obter(UsuarioId id) {
        notNull(id, "O ID do usuário não pode ser nulo");
        Usuario usuario = usuarioRepository.obter(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        notNull(email, "O email não pode ser nulo");
        notBlank(email, "O email não pode estar em branco");
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuario;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public List<Usuario> listarAtivos() {
        return usuarioRepository.listarAtivos();
    }

    public List<Usuario> listarPorTipo(Usuario.TipoUsuario tipo) {
        notNull(tipo, "O tipo de usuário não pode ser nulo");
        return usuarioRepository.listarPorTipo(tipo);
    }

    public List<Usuario> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        notNull(inicio, "A data de início não pode ser nula");
        notNull(fim, "A data de fim não pode ser nula");
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }
        return usuarioRepository.listarPorPeriodo(inicio, fim);
    }

    public void excluir(UsuarioId id) {
        notNull(id, "O ID do usuário não pode ser nulo");
        if (!usuarioRepository.existe(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.excluir(id);
    }

    public void desativar(UsuarioId id) {
        Usuario usuario = obter(id);
        usuario.desativar();
        usuarioRepository.salvar(usuario);
    }

    public void ativar(UsuarioId id) {
        Usuario usuario = obter(id);
        usuario.ativar();
        usuarioRepository.salvar(usuario);
    }

    public void tornarOrganizador(UsuarioId id) {
        Usuario usuario = obter(id);
        usuario.tornarOrganizador();
        usuarioRepository.salvar(usuario);
    }

    public void tornarParticipante(UsuarioId id) {
        Usuario usuario = obter(id);
        usuario.tornarParticipante();
        usuarioRepository.salvar(usuario);
    }

    public List<Usuario> listarOrganizadores() {
        return usuarioRepository.listarPorTipo(Usuario.TipoUsuario.ORGANIZADOR);
    }

    public List<Usuario> listarParticipantes() {
        return usuarioRepository.listarPorTipo(Usuario.TipoUsuario.PARTICIPANTE);
    }

    public boolean verificarCredenciais(String email, String senha) {
        notNull(email, "O email não pode ser nulo");
        notNull(senha, "A senha não pode ser nula");
        
        try {
            Usuario usuario = usuarioRepository.buscarPorEmail(email);
            return usuario != null && 
                   usuario.estaAtivo() && 
                   usuario.getSenha().equals(senha);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
} 