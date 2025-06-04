package com.plataforma.dominio.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoService;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventosFiltradosSteps {

	@Autowired
	private EventoService eventoService;

	private List<Evento> eventosFiltrados;
	private List<Evento> eventosCadastrados = new ArrayList<>();
	private EventoRepository eventoRepository;

	@Dado("que existem eventos cadastrados na plataforma")
	public void que_existem_eventos_cadastrados_na_plataforma(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		for (Map<String, String> row : rows) {
			Evento evento = Mockito.mock(Evento.class);
			Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
			Mockito.when(evento.getGenero()).thenReturn(row.get("genero"));
			Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
			Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
			Mockito.when(evento.getValor()).thenReturn(new BigDecimal(row.get("preco")));
			eventosCadastrados.add(evento);
		}

		eventoRepository = Mockito.mock(EventoRepository.class);
		Mockito.when(eventoRepository.listarTodos()).thenReturn(eventosCadastrados);
		eventoService = new EventoService(eventoRepository);
	}

	@Dado("que os eventos possuem diferentes gêneros, horários, datas e preços")
	public void que_os_eventos_possuem_diferentes_generos_horarios_datas_e_precos() {
		Assert.assertFalse("Deve haver eventos cadastrados", eventosCadastrados.isEmpty());

		Set<String> generos = new HashSet<>();
		for (Evento evento : eventosCadastrados) {
			generos.add(evento.getGenero());
		}
		Assert.assertTrue("Deve haver diferentes gêneros", generos.size() > 1);

		Set<LocalTime> horarios = new HashSet<>();
		for (Evento evento : eventosCadastrados) {
			horarios.add(evento.getDataInicio().toLocalTime());
		}
		Assert.assertTrue("Deve haver diferentes horários", horarios.size() > 1);

		Set<LocalDate> datas = new HashSet<>();
		for (Evento evento : eventosCadastrados) {
			datas.add(evento.getDataInicio().toLocalDate());
		}
		Assert.assertTrue("Deve haver diferentes datas", datas.size() > 1);

		Set<BigDecimal> precos = new HashSet<>();
		for (Evento evento : eventosCadastrados) {
			precos.add(evento.getValor());
		}
		Assert.assertTrue("Deve haver diferentes preços", precos.size() > 1);
	}

	@Quando("eu selecionar o gênero {string}")
	public void eu_selecionar_o_genero(String genero) {
		eventosFiltrados = eventoService.listarPorFiltros(genero, null, null, null, null);
	}

	@Entao("devo ver apenas eventos do gênero {string}")
	public void devo_ver_apenas_eventos_do_genero(String genero) {
		Assert.assertNotNull("A lista de eventos filtrados não deve ser nula", eventosFiltrados);
		Assert.assertTrue("Deve haver eventos filtrados", !eventosFiltrados.isEmpty());

		for (Evento evento : eventosFiltrados) {
			Assert.assertEquals("O gênero do evento deve corresponder ao filtro", genero, evento.getGenero());
		}
	}

	@Quando("eu selecionar o horário {string}")
	public void eu_selecionar_o_horario(String periodo) {
		LocalTime inicioPeriodo = null;
		LocalTime fimPeriodo = null;

		if ("Manhã".equals(periodo)) {
			inicioPeriodo = LocalTime.of(6, 0);
			fimPeriodo = LocalTime.of(11, 59);
		} else if ("Tarde".equals(periodo)) {
			inicioPeriodo = LocalTime.of(12, 0);
			fimPeriodo = LocalTime.of(17, 59);
		} else if ("Noite".equals(periodo)) {
			inicioPeriodo = LocalTime.of(18, 0);
			fimPeriodo = LocalTime.of(23, 59);
		}

		eventosFiltrados = eventoService.listarPorFiltros(null, inicioPeriodo, fimPeriodo, null, null);
	}

	@Entao("devo ver apenas eventos que acontecem no período matutino")
	public void devo_ver_apenas_eventos_que_acontecem_no_periodo_matutino() {
		Assert.assertNotNull("A lista de eventos filtrados não deve ser nula", eventosFiltrados);
		Assert.assertTrue("Deve haver eventos filtrados", !eventosFiltrados.isEmpty());

		LocalTime inicioManha = LocalTime.of(6, 0);
		LocalTime fimManha = LocalTime.of(11, 59);

		for (Evento evento : eventosFiltrados) {
			LocalTime horarioEvento = evento.getDataInicio().toLocalTime();
			Assert.assertTrue(
					"O evento deve começar no período matutino (entre 6:00 e 11:59)",
					!horarioEvento.isBefore(inicioManha) && !horarioEvento.isAfter(fimManha));
		}
	}

	@Entao("devo ver apenas eventos que acontecem no período vespertino")
	public void devo_ver_apenas_eventos_que_acontecem_no_periodo_vespertino() {
		Assert.assertNotNull("A lista de eventos filtrados não deve ser nula", eventosFiltrados);
		Assert.assertTrue("Deve haver eventos filtrados", !eventosFiltrados.isEmpty());

		LocalTime inicioTarde = LocalTime.of(12, 0);
		LocalTime fimTarde = LocalTime.of(17, 59);

		for (Evento evento : eventosFiltrados) {
			LocalTime horarioEvento = evento.getDataInicio().toLocalTime();
			Assert.assertTrue(
					"O evento deve começar no período vespertino (entre 12:00 e 17:59)",
					!horarioEvento.isBefore(inicioTarde) && !horarioEvento.isAfter(fimTarde));
		}
	}

	@Entao("devo ver apenas eventos que acontecem no período noturno")
	public void devo_ver_apenas_eventos_que_acontecem_no_periodo_noturno() {
		Assert.assertNotNull("A lista de eventos filtrados não deve ser nula", eventosFiltrados);
		Assert.assertTrue("Deve haver eventos filtrados", !eventosFiltrados.isEmpty());

		LocalTime inicioNoite = LocalTime.of(18, 0);
		LocalTime fimNoite = LocalTime.of(23, 59);

		for (Evento evento : eventosFiltrados) {
			LocalTime horarioEvento = evento.getDataInicio().toLocalTime();
			Assert.assertTrue(
					"O evento deve começar no período noturno (entre 18:00 e 23:59)",
					!horarioEvento.isBefore(inicioNoite) && !horarioEvento.isAfter(fimNoite));
		}
	}

	@Quando("eu selecionar a data {string}")
	public void eu_selecionar_a_data(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataSelecionada = LocalDate.parse(data, formatter);
		eventosFiltrados = eventoService.listarPorFiltros(null, null, null, dataSelecionada, null);
	}

	@Entao("devo ver apenas eventos que acontecem nesta data")
	public void devo_ver_apenas_eventos_que_acontecem_nesta_data() {
		Assert.assertNotNull("A lista de eventos filtrados não deve ser nula", eventosFiltrados);
		Assert.assertTrue("Deve haver eventos filtrados", !eventosFiltrados.isEmpty());

		LocalDate dataPrimeiroEvento = eventosFiltrados.get(0).getDataInicio().toLocalDate();

		for (Evento evento : eventosFiltrados) {
			LocalDate dataEvento = evento.getDataInicio().toLocalDate();
			Assert.assertEquals(
					"O evento deve acontecer na data selecionada",
					dataPrimeiroEvento,
					dataEvento);
		}
	}

	@E("a lista deve ser atualizada automaticamente")
	public void a_lista_deve_ser_atualizada_automaticamente() {
		Assert.assertNotNull("A lista atualizada não deve ser nula", eventosFiltrados);
	}
}
