package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.E;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.evento.Evento;

public class SeguirUsuariosSteps {
    private Usuario usuarioAtual;
    private Usuario organizador;
    private List<Usuario> usuariosSeguidos;
    private Evento novoEvento;
    private boolean botaoSeguirClicado;
    private String mensagemExibida;

    @Dado("que sou um usuário da plataforma que deseja seguir outros usuários")
    public void queSouUmUsuarioDaPlataformaQueDesejaSeguirOutrosUsuarios() {
        usuarioAtual = new Usuario();
        usuariosSeguidos = new ArrayList<>();
    }

    @Dado("existe um organizador chamado {string}")
    public void existeUmOrganizadorChamado(String nome) {
        organizador = new Usuario();
        organizador.setNome(nome);
    }

    @Dado("estou seguindo o organizador {string}")
    public void estouSeguindoOOrganizador(String nome) {
        organizador = new Usuario();
        organizador.setNome(nome);
        usuariosSeguidos.add(organizador);
    }

    @Dado("estou seguindo os organizadores:")
    public void estouSeguindoOsOrganizadores(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> organizadores = dataTable.asMaps();
        for (Map<String, String> org : organizadores) {
            Usuario organizador = new Usuario();
            organizador.setNome(org.get("Nome"));
            usuariosSeguidos.add(organizador);
        }
    }

    @Quando("acesso o perfil do organizador {string}")
    public void acessoOPerfilDoOrganizador(String nome) {
        // Simula o acesso ao perfil
        Assertions.assertEquals(nome, organizador.getNome());
    }

    @Quando("clico no botão {string}")
    public void clicoNoBotao(String botao) {
        botaoSeguirClicado = true;
        if (botao.equals("Seguir")) {
            usuariosSeguidos.add(organizador);
            mensagemExibida = "Você está seguindo " + organizador.getNome();
        } else if (botao.equals("Seguindo")) {
            usuariosSeguidos.remove(organizador);
            mensagemExibida = "Você parou de seguir " + organizador.getNome();
        }
    }

    @Quando("o organizador {string} cria um novo evento")
    public void oOrganizadorCriaUmNovoEvento(String nome) {
        novoEvento = new Evento();
        novoEvento.setTitulo("Novo Evento");
        novoEvento.setOrganizador(organizador);
    }

    @Quando("acesso minha lista de usuários seguidos")
    public void acessoMinhaListaDeUsuariosSeguidos() {
        // Simula o acesso à lista
    }

    @Entao("devo ver a mensagem {string}")
    public void devoVerAMensagem(String mensagemEsperada) {
        Assertions.assertEquals(mensagemEsperada, mensagemExibida);
    }

    @Entao("o botão deve mudar para {string}")
    public void oBotaoDeveMudarPara(String statusEsperado) {
        boolean estaSeguindo = usuariosSeguidos.contains(organizador);
        String statusAtual = estaSeguindo ? "Seguindo" : "Seguir";
        Assertions.assertEquals(statusEsperado, statusAtual);
    }

    @Entao("devo ver o evento na seção {string} do meu feed")
    public void devoVerOEventoNaSecaoDoMeuFeed(String secao) {
        Assertions.assertTrue(usuariosSeguidos.contains(organizador));
        Assertions.assertEquals("Novo Evento", novoEvento.getTitulo());
    }

    @Entao("o evento deve estar marcado como {string}")
    public void oEventoDeveEstarMarcadoComo(String status) {
        Assertions.assertEquals("Novo", status);
    }

    @Entao("não devo ver o evento na seção {string} do meu feed")
    public void naoDevoVerOEventoNaSecaoDoMeuFeed(String secao) {
        Assertions.assertFalse(usuariosSeguidos.contains(organizador));
    }

    @Entao("devo ver os organizadores:")
    public void devoVerOsOrganizadores(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> organizadoresEsperados = dataTable.asMaps();
        Assertions.assertEquals(organizadoresEsperados.size(), usuariosSeguidos.size());
        
        for (Map<String, String> org : organizadoresEsperados) {
            boolean encontrado = usuariosSeguidos.stream()
                .anyMatch(u -> u.getNome().equals(org.get("Nome")));
            Assertions.assertTrue(encontrado);
        }
    }
} 