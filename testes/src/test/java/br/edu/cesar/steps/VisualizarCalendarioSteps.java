package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.datatable.DataTable;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.interacao.Calendario;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class VisualizarCalendarioSteps {
    
    private Usuario usuario;
    private Calendario calendario;
    private List<Evento> eventosExibidos;
    private Evento eventoSelecionado;
    
    @Dado("que eu sou um usuário da plataforma")
    public void queEuSouUmUsuarioDaPlataforma() {
        usuario = new Usuario();
        calendario = new Calendario(usuario);
    }

    @Dado("tenho eventos salvos como interesse")
    public void tenhoEventosSalvosComoInteresse() {
        Evento evento = new Evento();
        evento.setTitulo("Show de Rock");
        calendario.adicionarEvento(evento, Calendario.TipoInteracao.SALVO);
    }

    @Dado("tenho eventos confirmados")
    public void tenhoEventosConfirmados() {
        Evento evento = new Evento();
        evento.setTitulo("Palestra de Tecnologia");
        calendario.adicionarEvento(evento, Calendario.TipoInteracao.CONFIRMADO);
    }

    @Dado("tenho eventos marcados como {string}")
    public void tenhoEventosMarcadosComo(String tipo) {
        Evento evento = new Evento();
        evento.setTitulo("Workshop de Design");
        calendario.adicionarEvento(evento, Calendario.TipoInteracao.TALVEZ);
    }

    @Dado("tenho eventos no meu calendário")
    public void tenhoEventosNoMeuCalendario() {
        Evento evento = new Evento();
        evento.setTitulo("Show de Rock");
        evento.setLocal("Arena Rock");
        evento.setDataHora(LocalDateTime.of(2024, 12, 1, 20, 0));
        evento.setDescricao("Show da banda X");
        calendario.adicionarEvento(evento, Calendario.TipoInteracao.CONFIRMADO);
    }

    @Quando("eu acessar meu calendário pessoal")
    public void euAcessarMeuCalendarioPessoal() {
        eventosExibidos = calendario.getEventos();
    }

    @Quando("eu clicar em um evento no calendário")
    public void euClicarEmUmEventoNoCalendario() {
        if (eventosExibidos == null || eventosExibidos.isEmpty()) {
            eventosExibidos = calendario.getEventos();
        }
        eventoSelecionado = eventosExibidos.get(0);
    }

    @Quando("eu alternar a visualização do calendário para {string}")
    public void euAlternarAVisualizacaoDoCalendarioPara(String visualizacao) {
        switch (visualizacao.toLowerCase()) {
            case "mensal":
                calendario.setVisualizacao(Calendario.TipoVisualizacao.MENSAL);
                break;
            case "semanal":
                calendario.setVisualizacao(Calendario.TipoVisualizacao.SEMANAL);
                break;
            case "diária":
                calendario.setVisualizacao(Calendario.TipoVisualizacao.DIARIA);
                break;
        }
    }

    @Então("devo ver os eventos salvos destacados com a cor azul")
    public void devoVerOsEventosSalvosDestacadosComACorAzul() {
        List<Evento> eventosSalvos = calendario.getEventosPorTipo(Calendario.TipoInteracao.SALVO);
        assertFalse("Deveria haver eventos salvos", eventosSalvos.isEmpty());
        assertEquals("A cor do evento salvo deve ser azul", 
            "Azul", calendario.getCorDoEvento(eventosSalvos.get(0)));
    }

    @Então("devo ver os eventos confirmados destacados com a cor verde")
    public void devoVerOsEventosConfirmadosDestacadosComACorVerde() {
        List<Evento> eventosConfirmados = calendario.getEventosPorTipo(Calendario.TipoInteracao.CONFIRMADO);
        assertFalse("Deveria haver eventos confirmados", eventosConfirmados.isEmpty());
        assertEquals("A cor do evento confirmado deve ser verde", 
            "Verde", calendario.getCorDoEvento(eventosConfirmados.get(0)));
    }

    @Então("devo ver os eventos marcados como {string} destacados com a cor amarela")
    public void devoVerOsEventosMarcadosComoDestacadosComACorAmarela(String tipo) {
        List<Evento> eventosTalvez = calendario.getEventosPorTipo(Calendario.TipoInteracao.TALVEZ);
        assertFalse("Deveria haver eventos marcados como talvez", eventosTalvez.isEmpty());
        assertEquals("A cor do evento marcado como talvez deve ser amarela", 
            "Amarelo", calendario.getCorDoEvento(eventosTalvez.get(0)));
    }

    @Então("devo ver os detalhes do evento:")
    public void devoVerOsDetalhesDoEvento(DataTable dataTable) {
        assertNotNull("O evento selecionado não deve ser nulo", eventoSelecionado);
        
        Map<String, String> detalhesEsperados = dataTable.asMap(String.class, String.class);
        
        assertEquals("O nome do evento deve corresponder", 
            detalhesEsperados.get("nome"), eventoSelecionado.getTitulo());
        assertEquals("O local do evento deve corresponder", 
            detalhesEsperados.get("local"), eventoSelecionado.getLocal());
        assertEquals("A data do evento deve corresponder", 
            detalhesEsperados.get("data"), eventoSelecionado.getDataHora().toLocalDate().toString());
        assertEquals("O horário do evento deve corresponder", 
            detalhesEsperados.get("horário"), eventoSelecionado.getDataHora().toLocalTime().toString());
        assertEquals("A descrição do evento deve corresponder", 
            detalhesEsperados.get("descrição"), eventoSelecionado.getDescricao());
    }

    @Então("devo ver os eventos organizados por {string}")
    public void devoVerOsEventosOrganizadosPor(String periodo) {
        Calendario.TipoVisualizacao visualizacaoEsperada;
        switch (periodo.toLowerCase()) {
            case "semana":
                visualizacaoEsperada = Calendario.TipoVisualizacao.SEMANAL;
                break;
            case "dia":
                visualizacaoEsperada = Calendario.TipoVisualizacao.DIARIA;
                break;
            case "mês":
                visualizacaoEsperada = Calendario.TipoVisualizacao.MENSAL;
                break;
            default:
                throw new IllegalArgumentException("Período inválido: " + periodo);
        }
        
        assertEquals("A visualização do calendário deve corresponder ao período selecionado",
            visualizacaoEsperada, calendario.getVisualizacaoAtual());
    }
} 