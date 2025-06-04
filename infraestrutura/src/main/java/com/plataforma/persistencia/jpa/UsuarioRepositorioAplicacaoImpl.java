package com.plataforma.persistencia.jpa;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

import com.plataforma.aplicacao.usuario.UsuarioResumo;
import com.plataforma.aplicacao.usuario.UsuarioResumoImpl;
import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositorioAplicacaoImpl implements UsuarioRepositorioAplicacao {
	private final UsuarioJpaRepository usuarioRepository;
	private final SeguidorJpaRepository seguidorRepository;
	private final JpaMapeador mapeador;

	private UsuarioJpa toJpa(UsuarioResumo usuario) {
		var jpa = new UsuarioJpa();
		jpa.setId(usuario.getId() != null ? Integer.parseInt(usuario.getId()) : null);
		jpa.setNome(usuario.getNome());
		jpa.setEmail(usuario.getEmail());
		jpa.setSenha(usuario.getSenha());
		return jpa;
	}

	private UsuarioResumoImpl toResumo(UsuarioJpa jpa) {
		var resumo = new UsuarioResumoImpl();
		resumo.setId(String.valueOf(jpa.getId()));
		resumo.setNome(jpa.getNome());
		resumo.setEmail(jpa.getEmail());
		resumo.setSenha(jpa.getSenha());
		return resumo;
	}

	@Override
	public void registrar(UsuarioResumo usuario) {
		var usuarioJpa = toJpa(usuario);
		usuarioRepository.save(usuarioJpa);
	}

	@Override
	public void salvar(UsuarioResumo usuario) {
		var usuarioJpa = toJpa(usuario);
		usuarioRepository.save(usuarioJpa);
	}

	@Override
	public Optional<UsuarioResumo> login(String email, String senha) {
		return usuarioRepository.findByEmailAndSenha(email, senha)
				.map(this::toResumo);
	}

	@Override
	public Optional<UsuarioResumo> buscarPorId(Integer id) {
		return usuarioRepository.findById(id)
				.map(this::toResumo);
	}

	@Override
	public void seguirUsuario(UsuarioResumo seguidor, UsuarioResumo seguido) {
		var seguidorJpa = toJpa(seguidor);
		var seguidoJpa = toJpa(seguido);

		if (seguidorRepository.existsBySeguidorAndSeguido(seguidorJpa, seguidoJpa)) {
			throw new IllegalStateException("Usuário já está seguindo");
		}

		var novoSeguidor = new SeguidorJpa();
		novoSeguidor.setSeguidor(seguidorJpa);
		novoSeguidor.setSeguido(seguidoJpa);
		
		seguidorRepository.save(novoSeguidor);
		
		// Atualiza o contador de seguidores
		seguidoJpa.setSeguidores(seguidoJpa.getSeguidores() + 1);
		usuarioRepository.save(seguidoJpa);
	}

	@Override
	public void deixarDeSeguir(UsuarioResumo seguidor, UsuarioResumo seguido) {
		var seguidorJpa = toJpa(seguidor);
		var seguidoJpa = toJpa(seguido);

		var relacao = seguidorRepository.findBySeguidorAndSeguido(seguidorJpa, seguidoJpa)
				.orElseThrow(() -> new IllegalStateException("Usuário não está seguindo"));

		seguidorRepository.delete(relacao);
		
		// Atualiza o contador de seguidores
		seguidoJpa.setSeguidores(seguidoJpa.getSeguidores() - 1);
		usuarioRepository.save(seguidoJpa);
	}

	@Override
	public boolean emailJaExiste(String email) {
		return usuarioRepository.findByEmail(email).isPresent();
	}
}