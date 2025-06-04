package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    void salvar(Usuario usuario);

    Optional<Usuario> obter(UsuarioId id);

    Optional<Usuario> login(String email, String senha);

    boolean emailJaExiste(String email);

    void seguirUsuario(UsuarioId id, UsuarioId idSeguido);

    List<Usuario> listarTodos();
}