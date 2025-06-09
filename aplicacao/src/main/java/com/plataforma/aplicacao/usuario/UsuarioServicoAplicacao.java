package com.plataforma.aplicacao.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicoAplicacao {

    private final UsuarioRepositorioAplicacao repositorio;

    @Transactional
    public void registrar(UsuarioResumo usuario) {
        if (repositorio.emailJaExiste(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        repositorio.salvar(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResumo> login(String email, String senha) {
        return repositorio.login(email, senha);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResumo> buscarPorId(Integer id) {
        return repositorio.buscarPorId(id);
    }

    @Transactional
    public void seguirUsuario(Integer seguidorId, Integer seguidoId) {
        if (seguidorId.equals(seguidoId)) {
            throw new IllegalArgumentException("Um usuário não pode seguir a si mesmo");
        }

        var seguidor = repositorio.buscarPorId(seguidorId)
            .orElseThrow(() -> new IllegalArgumentException("Seguidor não encontrado"));
            
        var seguido = repositorio.buscarPorId(seguidoId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário a ser seguido não encontrado"));

        // Cria instâncias de UsuarioResumoImpl para evitar problemas com o ModelMapper
        var seguidorImpl = new UsuarioResumoImpl();
        seguidorImpl.setId(seguidor.getId());
        seguidorImpl.setNome(seguidor.getNome());
        seguidorImpl.setEmail(seguidor.getEmail());
        seguidorImpl.setSenha(seguidor.getSenha());

        var seguidoImpl = new UsuarioResumoImpl();
        seguidoImpl.setId(seguido.getId());
        seguidoImpl.setNome(seguido.getNome());
        seguidoImpl.setEmail(seguido.getEmail());
        seguidoImpl.setSenha(seguido.getSenha());

        repositorio.seguirUsuario(seguidorImpl, seguidoImpl);
    }

    @Transactional
    public void deixarDeSeguir(Integer seguidorId, Integer seguidoId) {
        var seguidor = repositorio.buscarPorId(seguidorId)
            .orElseThrow(() -> new IllegalArgumentException("Seguidor não encontrado"));
            
        var seguido = repositorio.buscarPorId(seguidoId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário a ser seguido não encontrado"));
        var seguidorImpl = new UsuarioResumoImpl();
        seguidorImpl.setId(seguidor.getId());
        seguidorImpl.setNome(seguidor.getNome());
        seguidorImpl.setEmail(seguidor.getEmail());
        seguidorImpl.setSenha(seguidor.getSenha());

        var seguidoImpl = new UsuarioResumoImpl();
        seguidoImpl.setId(seguido.getId());
        seguidoImpl.setNome(seguido.getNome());
        seguidoImpl.setEmail(seguido.getEmail());
        seguidoImpl.setSenha(seguido.getSenha());

        repositorio.deixarDeSeguir(seguidorImpl, seguidoImpl);
    }

    @Transactional(readOnly = true)
    public java.util.List<UsuarioResumo> listarSeguidos(Integer usuarioId) {
        return repositorio.listarSeguidos(usuarioId);
    }
} 