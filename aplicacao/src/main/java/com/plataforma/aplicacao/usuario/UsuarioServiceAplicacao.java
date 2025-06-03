package com.plataforma.aplicacao.usuario;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plataforma.aplicacao.usuario.Usuario;
import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

@Service
public class UsuarioServiceAplicacao {
    private UsuarioRepositorioAplicacao repositorio;

    public UsuarioServiceAplicacao(UsuarioRepositorioAplicacao repositorio) {
        notNull(repositorio, "O repositório não pode ser nulo");

        this.repositorio = repositorio;
    }

    public List<Usuario> listarUsuarios() {
        return repositorio.listarUsuarios();
    }
}
