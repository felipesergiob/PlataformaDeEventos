package com.plataforma.persistencia.jpa;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.usuario.Usuario;
import com.plataforma.usuario.UsuarioRepository;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepository {
    @Autowired
    UsuarioJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public void salvar(Usuario usuario) {
        var usuarioJpa = mapeador.map(usuario, UsuarioJpa.class);
        repositorio.save(usuarioJpa);
    }

    @Override
    public Optional<Usuario> obter(UsuarioId id) {
        return repositorio.findById(id.getId())
            .map(usuarioJpa -> mapeador.map(usuarioJpa, Usuario.class));
    }

    @Override
    public Optional<Usuario> login(String email, String senha) {
        return repositorio.findByEmailAndSenha(email, senha)
            .map(usuarioJpa -> mapeador.map(usuarioJpa, Usuario.class));
    }

    @Override
    public boolean emailJaExiste(String email) {
        return repositorio.findByEmail(email).isPresent();
    }

    @Override
    public void seguirUsuario(UsuarioId id, UsuarioId idSeguido) {
        // Implementation will be added when we create the SEGUIDOR table mapping
    }

    @Override
    public List<Usuario> listarTodos() {
        var usuarios = repositorio.findByOrderByNome();
        return mapeador.map(usuarios, new TypeToken<List<Usuario>>() {}.getType());
    }
} 