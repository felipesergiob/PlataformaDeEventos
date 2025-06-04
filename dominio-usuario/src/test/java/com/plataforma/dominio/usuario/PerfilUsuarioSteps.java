package com.plataforma.dominio.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.usuario.Usuario;
import com.plataforma.usuario.UsuarioService;
import com.plataforma.usuario.UsuarioRepository;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PerfilUsuarioSteps {
	@Mock
	private UsuarioRepository usuarioRepository;
	@Mock
	private EventoRepository eventoRepository;

	private UsuarioService usuarioService;
	private List<Map<String, Object>> perfil;

	public PerfilUsuarioSteps() {
		MockitoAnnotations.openMocks(this);
		usuarioService = new UsuarioService(usuarioRepository, eventoRepository);
	}

	@Dado("que existe um usuário {string} com email {string}")
	public void queExisteUmUsuarioComEmail(String nome, String email) {
		Usuario usuario = new Usuario(
				UsuarioId.de(1),
				nome,
				email,
				"senha123",
				"11999999999"
		);
		when(usuarioRepository.obter(any())).thenReturn(Optional.of(usuario));
	}

	@Dado("que o usuário organizou os seguintes eventos:")
	public void queOUsuarioOrganizouOsSeguintesEventos(io.cucumber.datatable.DataTable dataTable) {
		List<Evento> eventos = new ArrayList<>();
		dataTable.asMaps().forEach(row -> {
			Evento evento = new Evento(
					EventoId.de(1),
					row.get("Nome do Evento"),
					"Descrição do evento",
					LocalDateTime.parse(row.get("Data Início").replace(" ", "T")),
					LocalDateTime.parse(row.get("Data Fim").replace(" ", "T")),
					"Local do evento",
					UsuarioId.de(1),
					"Gênero do evento",
					new BigDecimal("50.0")
			);
			eventos.add(evento);
		});
		when(eventoRepository.listarPorOrganizador(any())).thenReturn(eventos);
	}

	@Quando("eu visualizar o perfil do usuário")
	public void euVisualizarOPerfilDoUsuario() {
		perfil = usuarioService.perfilUsuario(UsuarioId.de(1));
	}

	@Entao("devo ver os seguintes eventos passados:")
	public void devoVerOsSeguintesEventosPassados(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> eventosEsperados = dataTable.asMaps();
		assertEquals(eventosEsperados.size(), perfil.size());

		for (int i = 0; i < eventosEsperados.size(); i++) {
			Map<String, String> eventoEsperado = eventosEsperados.get(i);
			Map<String, Object> eventoAtual = perfil.get(i);

			assertEquals(eventoEsperado.get("Nome do Evento"), eventoAtual.get("nome"));
			assertEquals(LocalDateTime.parse(eventoEsperado.get("Data Início").replace(" ", "T")), eventoAtual.get("dataInicio"));
			assertEquals(LocalDateTime.parse(eventoEsperado.get("Data Fim").replace(" ", "T")), eventoAtual.get("dataFim"));
		}
	}
}
