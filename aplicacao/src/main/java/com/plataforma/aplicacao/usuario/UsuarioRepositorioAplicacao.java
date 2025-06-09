package com.plataforma.aplicacao.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorioAplicacao {
    void registrar(UsuarioResumo usuario);
    void salvar(UsuarioResumo usuario);
    Optional<UsuarioResumo> login(String email, String senha);
    Optional<UsuarioResumo> buscarPorId(Integer id);
    void seguirUsuario(UsuarioResumo seguidor, UsuarioResumo seguido);
    void deixarDeSeguir(UsuarioResumo seguidor, UsuarioResumo seguido);
    boolean emailJaExiste(String email);
    List<UsuarioResumo> listarSeguidos(Integer usuarioId);
} 