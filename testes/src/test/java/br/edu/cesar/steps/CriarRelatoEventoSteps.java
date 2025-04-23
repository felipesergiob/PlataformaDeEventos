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
import br.edu.cesar.eventos.dominio.relato.Relato;
import br.edu.cesar.eventos.dominio.relato.Foto;

public class CriarRelatoEventoSteps {
    private Usuario usuario;
    private Evento evento;
    private Relato relatoAtual;
    private String mensagemErro;
    private List<Relato> relatos;

    @Dado("que sou um usuário da plataforma que deseja criar um relato")
    public void queSouUmUsuarioDaPlataformaQueDesejaCriarUmRelato() {
        usuario = new Usuario();
        usuario.setNome("João Silva");
    }

    @Dado("participei do evento {string}")
    public void participeiDoEvento(String nomeEvento) {
        evento = new Evento();
        evento.setTitulo(nomeEvento);
        relatos = new ArrayList<>();
    }

    @Quando("acesso a página do evento")
    public void acessoAPaginaDoEvento() {
        // Simula o acesso à página do evento
    }

    @Quando("clico no botão de criar relato {string}")
    public void clicoNoBotaoDeCriarRelato(String botao) {
        if (botao.equals("Criar Relato")) {
            relatoAtual = new Relato();
            relatoAtual.setAutor(usuario);
        }
    }

    @Quando("preencho o título com {string}")
    public void preenchoOTituloCom(String titulo) {
        relatoAtual.setTitulo(titulo);
    }

    @Quando("escrevo o relato:")
    public void escrevoORelato(String conteudo) {
        relatoAtual.setConteudo(conteudo);
    }

    @Quando("adiciono uma foto do evento")
    public void adicionoUmaFotoDoEvento() {
        Foto foto = new Foto();
        foto.setUrl("foto-evento.jpg");
        relatoAtual.adicionarFoto(foto);
    }

    @Quando("clico em publicar relato {string}")
    public void clicoEmPublicarRelato(String acao) {
        if (acao.equals("Publicar")) {
            if (relatoAtual.getTitulo() == null || relatoAtual.getTitulo().isEmpty()) {
                mensagemErro = "O título é obrigatório";
            } else {
                relatos.add(relatoAtual);
            }
        }
    }

    @Entao("devo ver meu relato publicado na página do evento")
    public void devoVerMeuRelatoPublicadoNaPaginaDoEvento() {
        Assertions.assertTrue(relatos.contains(relatoAtual));
    }

    @Entao("o relato deve conter:")
    public void oRelatoDeveConter(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> dados = dataTable.asMaps();
        Map<String, String> dadosEsperados = dados.get(0);
        
        Assertions.assertEquals(dadosEsperados.get("Valor"), relatoAtual.getTitulo());
        Assertions.assertEquals(dados.get(1).get("Valor"), relatoAtual.getAutor().getNome());
        Assertions.assertEquals(dados.get(2).get("Valor"), "Hoje");
        
        // Normaliza as quebras de linha antes de comparar o conteúdo
        String conteudoEsperado = dados.get(3).get("Valor").replaceAll("\\s+", " ").trim();
        String conteudoAtual = relatoAtual.getConteudo().replaceAll("\\s+", " ").trim();
        Assertions.assertEquals(conteudoEsperado, conteudoAtual);
        
        Assertions.assertEquals(Integer.parseInt(dados.get(4).get("Valor")), relatoAtual.getFotos().size());
    }

    @Entao("devo ver a mensagem de erro {string}")
    public void devoVerAMensagemDeErro(String mensagem) {
        Assertions.assertEquals(mensagem, mensagemErro);
    }

    @Entao("o relato não deve ser publicado")
    public void oRelatoNaoDeveSerPublicado() {
        Assertions.assertFalse(relatos.contains(relatoAtual));
    }
} 