package com.plataforma.usuario;

import java.util.List;
import java.util.Optional;

import com.plataforma.compartilhado.UsuarioId;

import java.time.LocalDateTime;

public interface UsuarioRepository {
    void salvar(Usuario usuario);
    Optional<Usuario> obter(UsuarioId id);
    List<Usuario> listarTodos();
    List<Usuario> listarAtivos();
    List<Usuario> listarPorTipo(Usuario.TipoUsuario tipo);
    List<Usuario> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    Usuario buscarPorEmail(String email);
    boolean existeEmail(String email);
    void excluir(UsuarioId id);
    boolean existe(UsuarioId id);
} 