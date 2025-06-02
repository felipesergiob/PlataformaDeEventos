package com.plataforma.dominio.evento;

import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoService;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventoDestaqueSteps {

    @Autowired
    private EventoService eventoService;

    private List<Evento> eventosDestaques;
    private List<Evento> eventosCadastrados = new ArrayList<>();

    @Dado("os seguintes eventos cadastrados:")
    public void os_seguinte_eventos_cadastrados(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map<String, String> row : rows) {
            Evento evento = Mockito.mock(Evento.class);
            Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
            Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
            Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
            Mockito.when(evento.getInscritos()).thenReturn(Integer.parseInt(row.get("inscritos")));
            eventosCadastrados.add(evento);
        }

        EventoRepository repo = Mockito.mock(EventoRepository.class);
        Mockito.when(repo.listarTodos()).thenReturn(eventosCadastrados);
        eventoService = new EventoService(repo);
    }

    @Quando("eu solicitar os eventos em destaque da semana")
    public void eu_solicitar_os_eventos_em_destaque_da_semana() {
        eventosDestaques = eventoService.listarEventosDestaques();
    }

    @Então("devo ver {int} eventos")
    public void devo_ver_eventos(Integer quantidade) {
        Assert.assertEquals(quantidade.intValue(), eventosDestaques.size());
    }

    @E("os eventos devem estar ordenados do mais para o menos popular")
    public void os_eventos_devem_estar_ordenados_do_mais_para_o_menos_popular() {
        for (int i = 0; i < eventosDestaques.size() - 1; i++) {
            Assert.assertTrue(eventosDestaques.get(i).getInscritos() >= eventosDestaques.get(i + 1).getInscritos());
        }
    }

    @E("todos os eventos exibidos devem ocorrer na semana atual")
    public void todos_os_eventos_exibidos_devem_ocorrer_na_semana_atual() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioSemana = agora.with(java.time.DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0)
                .withNano(0);
        LocalDateTime fimSemana = inicioSemana.plusDays(7);

        for (Evento evento : eventosDestaques) {
            LocalDateTime dataEvento = evento.getDataInicio();
            Assert.assertTrue(
                    "Evento " + evento.getNome() + " deve estar na semana atual",
                    !dataEvento.isBefore(inicioSemana) && dataEvento.isBefore(fimSemana));
        }
    }
}