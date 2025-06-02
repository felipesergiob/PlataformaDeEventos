// package com.plataforma.dominio.evento;

// import com.plataforma.evento.Evento;
// import com.plataforma.evento.EventoService;
// import com.plataforma.evento.EventoRepository;
// import com.plataforma.avaliacao.AvaliacaoService;
// import com.plataforma.avaliacao.AvaliacaoRepository;
// import com.plataforma.avaliacao.Avaliacao;
// import com.plataforma.compartilhado.EventoId;
// import com.plataforma.compartilhado.UsuarioId;
// import io.cucumber.java.pt.Dado;
// import io.cucumber.java.pt.E;
// import io.cucumber.java.pt.Entao;
// import io.cucumber.java.pt.Quando;
// import org.junit.jupiter.api.Assertions;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.LocalDate;
// import java.math.BigDecimal;
// import java.util.Map;
// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.mockito.Mockito;

// public class DashborardEventoSteps {
// 	private Evento evento;
// 	private AvaliacaoService avaliacaoService;
// 	private AvaliacaoRepository avaliacaoRepository;
// 	private Map<String, Object> dashboard;
// 	private Exception exception;

// 	@Autowired
// 	private EventoService eventoService;

// 	@Autowired
// 	private EventoRepository eventoRepository;

// 	public DashborardEventoSteps() {
// 		eventoRepository = Mockito.mock(EventoRepository.class);
// 		avaliacaoRepository = Mockito.mock(AvaliacaoRepository.class);
// 		avaliacaoService = Mockito.mock(AvaliacaoService.class);
// 		eventoService = new EventoService(eventoRepository, avaliacaoService, avaliacaoRepository);
// 	}

// 	@Dado("que existe um evento encerrado {string}")
// 	public void queExisteUmEventoEncerrado(String nomeEvento) {
// 		EventoId id = EventoId.de(1);
// 		UsuarioId organizador = UsuarioId.de(1);
// 		evento = Mockito.mock(Evento.class);
// 		Mockito.when(evento.getId()).thenReturn(id);
// 		Mockito.when(evento.getNome()).thenReturn(nomeEvento);
// 		Mockito.when(evento.getOrganizador()).thenReturn(organizador);
// 		Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.now().minusDays(1));
// 		Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.now().minusHours(12));
// 		Mockito.when(eventoRepository.obter(id)).thenReturn(evento);
// 		Mockito.doNothing().when(eventoRepository).salvar(evento);
// 	}

// 	@E("o evento possui os seguintes dados:")
// 	public void oEventoPossuiOsSeguintesDados(io.cucumber.datatable.DataTable dataTable) {
// 		List<Map<String, String>> dados = dataTable.asMaps();
// 		Map<String, String> dadosEvento = dados.get(0);

// 		LocalDateTime dataInicio = LocalDateTime.parse(dadosEvento.get("dataInicio"));
// 		LocalDateTime dataFim = LocalDateTime.parse(dadosEvento.get("dataFim"));
// 		Mockito.when(evento.getDataInicio()).thenReturn(dataInicio);
// 		Mockito.when(evento.getDataFim()).thenReturn(dataFim);

// 		// Campos opcionais
// 		if (dadosEvento.containsKey("totalInscritos")) {
// 			int totalInscritos = Integer.parseInt(dadosEvento.get("totalInscritos"));
// 			Mockito.when(evento.getInscritos()).thenReturn(totalInscritos);
// 		}

// 		// Configurando o mock do AvaliacaoService para retornar a média de avaliações
// 		if (dadosEvento.containsKey("mediaNotas")) {
// 			double mediaNotas = Double.parseDouble(dadosEvento.get("mediaNotas"));
// 			Mockito.when(avaliacaoService.obterMediaAvaliacoes(evento.getId()))
// 				.thenReturn(mediaNotas);
// 		}

// 		// Configurando o mock do AvaliacaoRepository para retornar a lista de avaliações
// 		if (dadosEvento.containsKey("avaliacoes")) {
// 			int quantidadeAvaliacoes = Integer.parseInt(dadosEvento.get("avaliacoes"));
// 			List<Avaliacao> avaliacoes = new ArrayList<>();
// 			for (int i = 0; i < quantidadeAvaliacoes; i++) {
// 				Avaliacao avaliacao = Mockito.mock(Avaliacao.class);
// 				Mockito.when(avaliacao.getComentario()).thenReturn("Comentário " + i);
// 				avaliacoes.add(avaliacao);
// 			}
// 			Mockito.when(avaliacaoRepository.listarNotasEvento(evento.getId()))
// 				.thenReturn(avaliacoes);
// 		}

// 		Mockito.doNothing().when(eventoRepository).salvar(evento);
// 	}

// 	@Quando("eu acessar o dashboard do evento")
// 	public void euAcessarODashboardDoEvento() {
// 		dashboard = eventoService.dashboardEvento(evento.getId());
// 	}

// 	@Entao("devo ver o número total de inscritos: {int}")
// 	public void devoVerONumeroTotalDeInscritos(int totalInscritos) {
// 		Assertions.assertEquals(totalInscritos, dashboard.get("totalInscritos"));
// 	}

// 	@E("o número de avaliações recebidas: {int}")
// 	public void oNumeroDeAvaliacoesRecebidas(int avaliacoes) {
// 		Assertions.assertEquals(avaliacoes, dashboard.get("quantidadeAvaliacoes"));
// 	}

// 	@E("a média de notas: {double}")
// 	public void aMediaDeNotas(double mediaNotas) {
// 		Assertions.assertEquals(mediaNotas, dashboard.get("mediaAvaliacoes"));
// 	}

// 	@Dado("que existe um evento {string} que ainda não aconteceu")
// 	public void queExisteUmEventoQueAindaNaoAconteceu(String nomeEvento) {
// 		EventoId id = EventoId.de(2);
// 		UsuarioId organizador = UsuarioId.de(1);
// 		evento = new Evento(id, nomeEvento, "Descrição do evento",
// 				LocalDateTime.now().plusDays(1),
// 				LocalDateTime.now().plusDays(1).plusHours(2),
// 				"Local do evento", organizador, "Gênero", BigDecimal.ZERO);
// 		eventoRepository.salvar(evento);
// 	}

// 	@Quando("eu tentar acessar o dashboard do evento")
// 	public void euTentarAcessarODashboardDoEvento() {
// 		try {
// 			dashboard = eventoService.dashboardEvento(evento.getId());
// 		} catch (Exception e) {
// 			exception = e;
// 		}
// 	}

// 	@Entao("devo receber uma mensagem informando que o dashboard só está disponível para eventos encerrados")
// 	public void devoReceberUmaMensagemInformandoQueODashboardSoEstaDisponivelParaEventosEncerrados() {
// 		Assertions.assertNull(dashboard);
// 		Assertions.assertNotNull(exception);
// 	}

// 	@Dado("que existe um evento {string} em andamento")
// 	public void queExisteUmEventoEmAndamento(String nomeEvento) {
// 		EventoId id = EventoId.de(3);
// 		UsuarioId organizador = UsuarioId.de(1);
// 		evento = new Evento(id, nomeEvento, "Descrição do evento",
// 				LocalDateTime.now().minusHours(1),
// 				LocalDateTime.now().plusHours(1),
// 				"Local do evento", organizador, "Gênero", BigDecimal.ZERO);
// 		eventoRepository.salvar(evento);
// 	}
// }
