package com.plataforma.dominio.usuario;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.usuario.UsuarioService;
import com.plataforma.usuario.UsuarioRepository;
import com.plataforma.evento.Evento;
import com.plataforma.evento.EventoRepository;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VisualizarCalendarioSteps {

    @Autowired
    private UsuarioService usuarioService;
    
    private List<Evento> eventosCalendario;
    private List<Evento> eventosSalvos = new ArrayList<>();
    private List<Evento> eventosConfirmados = new ArrayList<>();
    private UsuarioId usuarioId;

    @Dado("que existem os seguintes eventos salvos para o usuário {string}:")
    public void que_existem_os_seguintes_eventos_salvos(String id, io.cucumber.datatable.DataTable dataTable) {
        this.usuarioId = UsuarioId.de(Integer.parseInt(id));
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map<String, String> row : rows) {
            Evento evento = Mockito.mock(Evento.class);
            Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
            Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
            Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
            eventosSalvos.add(evento);
        }
    }

    @E("que existem os seguintes eventos confirmados para o usuário {string}:")
    public void que_existem_os_seguintes_eventos_confirmados(String id, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map<String, String> row : rows) {
            Evento evento = Mockito.mock(Evento.class);
            Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
            Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
            Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
            eventosConfirmados.add(evento);
        }

        EventoRepository repo = Mockito.mock(EventoRepository.class);
        Mockito.when(repo.listarSalvos(usuarioId)).thenReturn(eventosSalvos);
        Mockito.when(repo.listarConfirmados(usuarioId)).thenReturn(eventosConfirmados);
        usuarioService = new UsuarioService(Mockito.mock(UsuarioRepository.class), repo);
    }

    @Quando("eu solicitar a visualização do calendário para o usuário {string}")
    public void eu_solicitar_a_visualizacao_do_calendario(String id) {
        eventosCalendario = usuarioService.visualizarCalendario(usuarioId);
    }

    @Então("devo ver {int} eventos no calendário")
    public void devo_ver_eventos_no_calendario(Integer quantidade) {
        Assert.assertEquals(quantidade.intValue(), eventosCalendario.size());
    }

    @E("todos os eventos devem estar ordenados por data de início")
    public void todos_os_eventos_devem_estar_ordenados_por_data_de_inicio() {
        for (int i = 0; i < eventosCalendario.size() - 1; i++) {
            Assert.assertTrue(
                "Eventos devem estar ordenados por data de início",
                !eventosCalendario.get(i).getDataInicio().isAfter(eventosCalendario.get(i + 1).getDataInicio())
            );
        }
    }

    @E("o calendário deve conter tanto eventos salvos quanto confirmados")
    public void o_calendario_deve_conter_tanto_eventos_salvos_quanto_confirmados() {
        Set<String> nomesEventosSalvos = new HashSet<>();
        Set<String> nomesEventosConfirmados = new HashSet<>();
        
        eventosSalvos.forEach(e -> nomesEventosSalvos.add(e.getNome()));
        eventosConfirmados.forEach(e -> nomesEventosConfirmados.add(e.getNome()));
        
        for (Evento evento : eventosCalendario) {
            Assert.assertTrue(
                "Evento deve estar na lista de salvos ou confirmados",
                nomesEventosSalvos.contains(evento.getNome()) || nomesEventosConfirmados.contains(evento.getNome())
            );
        }
    }
} 