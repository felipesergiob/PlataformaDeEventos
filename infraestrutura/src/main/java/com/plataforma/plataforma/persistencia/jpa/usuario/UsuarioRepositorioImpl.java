package com.plataforma.plataforma.persistencia.jpa.usuario;

import com.plataforma.usuario.Usuario;
import com.plataforma.usuario.UsuarioRepository;
import com.plataforma.compartilhado.UsuarioId;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepository {
    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositorioImpl(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Usuario usuario) {
        UsuarioJpa usuarioJpa = new UsuarioJpa();
        usuarioJpa.setId(usuario.getId().getValor());
        usuarioJpa.setNome(usuario.getNome());
        usuarioJpa.setEmail(usuario.getEmail());
        usuarioJpa.setSenha(usuario.getSenha());
        usuarioJpa.setDataCriacao(usuario.getDataCriacao());
        usuarioJpa.setUltimoAcesso(usuario.getUltimoAcesso());
        usuarioJpa.setAtivo(usuario.isAtivo());
        
        jpaRepository.save(usuarioJpa);
    }

    @Override
    public Usuario obter(UsuarioId id) {
        return jpaRepository.findById(id.getValor())
            .map(this::toUsuario)
            .orElse(null);
    }

    @Override
    public Usuario obterPorEmail(String email) {
        return Optional.ofNullable(jpaRepository.findByEmail(email))
            .map(this::toUsuario)
            .orElse(null);
    }

    private Usuario toUsuario(UsuarioJpa jpa) {
        return new Usuario(
            new UsuarioId(jpa.getId()),
            jpa.getNome(),
            jpa.getEmail(),
            jpa.getSenha(),
            jpa.getDataCriacao(),
            jpa.getUltimoAcesso(),
            jpa.isAtivo()
        );
    }
} 