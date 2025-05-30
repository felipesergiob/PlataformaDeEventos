package com.plataforma.dominio.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.usuario.UsuarioService;
import com.plataforma.usuario.UsuarioRepository;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import com.plataforma.avaliacao.Avaliacao;
import com.plataforma.avaliacao.AvaliacaoRepository;
import com.plataforma.Publicacao.Publicacao;
import com.plataforma.Publicacao.PublicacaoRepository;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class PerfilUsuarioSteps {

	@Autowired
	private UsuarioService usuarioService;

	private List<Map<String, Object>> perfilInfo;
	private List<Evento> eventosPassados = new ArrayList<>();
	private List<Avaliacao> avaliacoes = new ArrayList<>();
	private List<Publicacao> publicacoes = new ArrayList<>();
	private UsuarioId usuarioId;
	private Evento eventoAtual;

	@Dado("que existem eventos passados organizados pelo usuário {string}")
	public void que_existem_eventos_passados_organizados_por(String id) {
		this.usuarioId = UsuarioId.de(Integer.parseInt(id));
	}

	@E("que o evento {string} foi organizado pelo usuário {string}")
	public void que_o_evento_foi_organizado_por(String nomeEvento, String id) {
		eventoAtual = Mockito.mock(Evento.class);
		Mockito.when(eventoAtual.getNome()).thenReturn(nomeEvento);
		eventosPassados.add(eventoAtual);
	}

	@E("que o evento {string} aconteceu em {string}")
	public void que_o_evento_aconteceu_em(String nomeEvento, String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(data, formatter);
		LocalDateTime dataEvento = localDate.atStartOfDay();
		Mockito.when(eventoAtual.getDataFim()).thenReturn(dataEvento);
	}

	@E("que o evento {string} recebeu {int} avaliações com notas {int}, {int} e {int}")
	public void que_o_evento_recebeu_avaliacoes_com_notas(String nomeEvento, int quantidade, int nota1, int nota2,
			int nota3) {
		Avaliacao avaliacao1 = Mockito.mock(Avaliacao.class);
		Avaliacao avaliacao2 = Mockito.mock(Avaliacao.class);
		Avaliacao avaliacao3 = Mockito.mock(Avaliacao.class);

		Mockito.when(avaliacao1.getNota()).thenReturn(nota1);
		Mockito.when(avaliacao2.getNota()).thenReturn(nota2);
		Mockito.when(avaliacao3.getNota()).thenReturn(nota3);

		avaliacoes.add(avaliacao1);
		avaliacoes.add(avaliacao2);
		avaliacoes.add(avaliacao3);
	}

	@E("que o evento {string} possui os seguintes comentários:")
	public void que_o_evento_possui_os_seguintes_comentarios(String nomeEvento,
			io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		for (int i = 0; i < rows.size(); i++) {
			Map<String, String> row = rows.get(i);
			Avaliacao avaliacao = avaliacoes.get(i);
			Mockito.when(avaliacao.getComentario()).thenReturn(row.get("comentario"));
		}
	}

	@E("que existem publicações relacionadas ao evento {string}")
	public void que_existem_publicacoes_relacionadas_ao_evento(String nomeEvento) {
		Publicacao publicacao = Mockito.mock(Publicacao.class);
		Mockito.when(publicacao.getEventoId()).thenReturn(eventoAtual.getId());
		Mockito.when(publicacao.getConteudo()).thenReturn("Conteúdo da publicação");
		publicacoes.add(publicacao);
	}

	@Quando("eu acesso o perfil do usuário {string}")
	public void eu_acesso_o_perfil_de(String id) {
		EventoRepository eventoRepo = Mockito.mock(EventoRepository.class);
		AvaliacaoRepository avaliacaoRepo = Mockito.mock(AvaliacaoRepository.class);
		PublicacaoRepository publicacaoRepo = Mockito.mock(PublicacaoRepository.class);

		Mockito.when(eventoRepo.listarPorOrganizador(usuarioId)).thenReturn(eventosPassados);
		Mockito.when(avaliacaoRepo.listarPorEvento(Mockito.any())).thenReturn(avaliacoes);
		Mockito.when(publicacaoRepo.listarPorAutor(usuarioId)).thenReturn(publicacoes);

		usuarioService = new UsuarioService(
				Mockito.mock(UsuarioRepository.class),
				eventoRepo,
				avaliacaoRepo,
				publicacaoRepo);

		perfilInfo = usuarioService.perfilUsuario(usuarioId);
	}

	@Então("eu devo ver a lista de eventos passados organizados por ele")
	public void eu_devo_ver_a_lista_de_eventos_passados_organizados_por_ele() {
		Assert.assertFalse("A lista de eventos não deve estar vazia", perfilInfo.isEmpty());
	}

	@E("eu devo ver que o evento {string} tem uma média de avaliação de {double}")
	public void eu_devo_ver_que_o_evento_tem_uma_media_de_avaliacao(String nomeEvento, double mediaEsperada) {
		Optional<Map<String, Object>> eventoInfo = perfilInfo.stream()
				.filter(info -> nomeEvento.equals(info.get("nome")))
				.findFirst();
		Assert.assertTrue("Evento não encontrado no perfil", eventoInfo.isPresent());
		Assert.assertEquals("Média de avaliação incorreta",
				mediaEsperada,
				(double) eventoInfo.get().get("mediaNotas"),
				0.001);
	}

	@E("eu devo ver os comentários dos participantes do evento {string}")
	public void eu_devo_ver_os_comentarios_dos_participantes_do_evento(String nomeEvento) {
		Optional<Map<String, Object>> eventoInfo = perfilInfo.stream()
				.filter(info -> nomeEvento.equals(info.get("nome")))
				.findFirst();

		Assert.assertTrue("Evento não encontrado no perfil", eventoInfo.isPresent());
		List<String> comentarios = (List<String>) eventoInfo.get().get("comentarios");
		Assert.assertFalse("Lista de comentários não deve estar vazia", comentarios.isEmpty());
	}

	@E("eu devo ver as publicações relacionadas ao evento {string}")
	public void eu_devo_ver_as_publicacoes_relacionadas_ao_evento(String nomeEvento) {
		Optional<Map<String, Object>> eventoInfo = perfilInfo.stream()
				.filter(info -> nomeEvento.equals(info.get("nome")))
				.findFirst();

		Assert.assertTrue("Evento não encontrado no perfil", eventoInfo.isPresent());
		List<Publicacao> publicacoesEvento = (List<Publicacao>) eventoInfo.get().get("publicacoes");
		Assert.assertFalse("Lista de publicações não deve estar vazia", publicacoesEvento.isEmpty());
	}
}
