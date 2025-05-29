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

public class SeguirUsuarioSteps {

    @Autowired
    private UsuarioService usuarioService;
    
    private List<Evento> eventosCadastrados = new ArrayList<>();
    private UsuarioId usuarioId;
    private UsuarioId usuarioSeguidoId;
    private Exception exception;

    @Dado("que existem os seguintes eventos do usuário {string}:")
    public void que_existem_os_seguintes_eventos_do_usuario(String id, io.cucumber.datatable.DataTable dataTable) {
        this.usuarioSeguidoId = UsuarioId.de(Integer.parseInt(id));
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map<String, String> row : rows) {
            Evento evento = Mockito.mock(Evento.class);
            Mockito.when(evento.getNome()).thenReturn(row.get("nome"));
            Mockito.when(evento.getDataInicio()).thenReturn(LocalDateTime.parse(row.get("dataInicio"), formatter));
            Mockito.when(evento.getDataFim()).thenReturn(LocalDateTime.parse(row.get("dataFim"), formatter));
            Mockito.when(evento.getStatus()).thenReturn(Evento.Status.valueOf(row.get("status")));
            eventosCadastrados.add(evento);
        }

        EventoRepository eventoRepo = Mockito.mock(EventoRepository.class);
        Mockito.when(eventoRepo.listarPorOrganizador(usuarioSeguidoId)).thenReturn(eventosCadastrados);
        usuarioService = new UsuarioService(Mockito.mock(UsuarioRepository.class), eventoRepo);
    }

    @Quando("eu seguir o usuário {string} sendo o usuário {string}")
    public void eu_seguir_o_usuario(String idSeguido, String id) {
        this.usuarioId = UsuarioId.de(Integer.parseInt(id));
        usuarioService.seguirUsuarioEListarEventos(usuarioId, usuarioSeguidoId);
    }

    @Quando("eu tentar seguir o usuário {string} sendo o usuário {string}")
    public void eu_tentar_seguir_o_usuario(String idSeguido, String id) {
        this.usuarioId = UsuarioId.de(Integer.parseInt(id));
        try {
            usuarioService.seguirUsuarioEListarEventos(usuarioId, usuarioId);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Então("devo ver {int} eventos do usuário seguido")
    public void devo_ver_eventos_do_usuario_seguido(Integer quantidade) {
        Assert.assertEquals(quantidade.intValue(), eventosCadastrados.size());
    }

    @E("os eventos devem estar ordenados por data de início")
    public void os_eventos_devem_estar_ordenados_por_data_de_inicio() {
        for (int i = 0; i < eventosCadastrados.size() - 1; i++) {
            Assert.assertTrue(
                "Eventos devem estar ordenados por data de início",
                !eventosCadastrados.get(i).getDataInicio().isAfter(eventosCadastrados.get(i + 1).getDataInicio())
            );
        }
    }

    @Então("devo receber um erro informando que não posso seguir a mim mesmo")
    public void devo_receber_um_erro_informando_que_nao_posso_seguir_a_mim_mesmo() {
        Assert.assertNotNull("Deveria ter lançado uma exceção", exception);
        Assert.assertEquals("O usuário não pode seguir a si mesmo", exception.getMessage());
    }
} 