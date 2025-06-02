package com.plataforma.dominio.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.Publicacao.PublicacaoId;
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
import java.math.BigDecimal;

public class PerfilUsuarioSteps {

	@Autowired
	private UsuarioService usuarioService;

	private List<Map<String, Object>> perfilInfo;
	private List<Evento> eventosPassados = new ArrayList<>();
	private List<Avaliacao> avaliacoes = new ArrayList<>();
	private List<Publicacao> publicacoes = new ArrayList<>();
	private UsuarioId usuarioId;
	private Evento eventoAtual;

	@Dado("que existe um usuário organizador")
	public void que_existe_um_usuario_organizador() {
		usuarioId = UsuarioId.de(1);
	}

	@E("que ele organizou os seguintes eventos passados:")
	public void que_ele_organizou_os_seguintes_eventos_passados(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		for (Map<String, String> row : rows) {
			Evento evento = Mockito.mock(Evento.class);
			Mockito.when(evento.getId()).thenReturn(EventoId.de(Integer.parseInt(row.get("id"))));
			Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
			Mockito.when(evento.getDescricao()).thenReturn(row.get("descricao"));
			Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
			Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
			Mockito.when(evento.getLocal()).thenReturn(row.get("local"));
			Mockito.when(evento.getGenero()).thenReturn(row.get("genero"));
			Mockito.when(evento.getValor()).thenReturn(new BigDecimal(row.get("valor")));
			eventosPassados.add(evento);
		}
	}

	@E("que existem as seguintes avaliações para os eventos:")
	public void que_existem_as_seguintes_avaliacoes_para_os_eventos(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> row : rows) {
			Avaliacao avaliacao = Mockito.mock(Avaliacao.class);
			Mockito.when(avaliacao.getNota()).thenReturn(Integer.parseInt(row.get("nota")));
			Mockito.when(avaliacao.getComentario()).thenReturn(row.get("comentario"));
			avaliacoes.add(avaliacao);
		}
	}

	@E("que ele fez as seguintes publicações:")
	public void que_ele_fez_as_seguintes_publicacoes(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> row : rows) {
			Publicacao publicacao = Mockito.mock(Publicacao.class);
			Mockito.when(publicacao.getId()).thenReturn(PublicacaoId.gerar());
			Mockito.when(publicacao.getEventoId()).thenReturn(EventoId.de(Integer.parseInt(row.get("eventoId"))));
			Mockito.when(publicacao.getConteudo()).thenReturn(row.get("conteudo"));
			publicacoes.add(publicacao);
		}
	}

	@Quando("eu acessar o perfil público do usuário")
	public void eu_acessar_o_perfil_publico_do_usuario() {
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

	@Entao("devo ver a lista de eventos passados organizados por ele")
	public void devo_ver_a_lista_de_eventos_passados_organizados_por_ele() {
		Assert.assertFalse("A lista de eventos não deve estar vazia", perfilInfo.isEmpty());
	}

	@E("para cada evento devo ver o nome, data de início e fim")
	public void para_cada_evento_devo_ver_o_nome_data_de_inicio_e_fim() {
		for (Map<String, Object> eventoInfo : perfilInfo) {
			Assert.assertNotNull("Nome do evento não deve ser nulo", eventoInfo.get("nome"));
			Assert.assertNotNull("Data de início não deve ser nula", eventoInfo.get("dataInicio"));
			Assert.assertNotNull("Data de fim não deve ser nula", eventoInfo.get("dataFim"));
		}
	}

	@E("a nota média e comentários das avaliações")
	public void a_nota_media_e_comentarios_das_avaliacoes() {
		for (Map<String, Object> eventoInfo : perfilInfo) {
			Assert.assertNotNull("Média de notas não deve ser nula", eventoInfo.get("mediaNotas"));
			Assert.assertNotNull("Comentários não devem ser nulos", eventoInfo.get("comentarios"));
			List<String> comentarios = (List<String>) eventoInfo.get("comentarios");
			Assert.assertFalse("Lista de comentários não deve estar vazia", comentarios.isEmpty());
		}
	}

	@E("as publicações feitas por ele")
	public void as_publicacoes_feitas_por_ele() {
		for (Map<String, Object> eventoInfo : perfilInfo) {
			Assert.assertNotNull("Publicações não devem ser nulas", eventoInfo.get("publicacoes"));
			List<Publicacao> publicacoesEvento = (List<Publicacao>) eventoInfo.get("publicacoes");
			Assert.assertFalse("Lista de publicações não deve estar vazia", publicacoesEvento.isEmpty());
		}
	}
}
