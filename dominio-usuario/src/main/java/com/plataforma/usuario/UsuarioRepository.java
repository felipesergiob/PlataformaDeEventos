package com.plataforma.usuario;

import com.plataforma.compartilhado.UsuarioId;

public interface UsuarioRepository {
    void salvar(Usuario usuario);

    void obter(UsuarioId id);

    void seguirUsuario(UsuarioId id, UsuarioId idSeguido);
}