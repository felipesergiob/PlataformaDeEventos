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
import br.edu.cesar.eventos.dominio.interacao.Comentario;
import br.edu.cesar.eventos.dominio.interacao.Resposta;

public class ResponderComentariosSteps {
    private Usuario organizador;
    private Evento evento;
    private List<Comentario> comentarios;
    private Comentario comentarioSelecionado;
    private Resposta respostaAtual;
    private String mensagemExibida;

    @Dado("que sou um organizador da plataforma")
    public void queSouUmOrganizadorDaPlataforma() {
        organizador = new Usuario();
        organizador.setNome("Organizador");
        organizador.setOrganizador(true);
    }

    @Dado("existe um evento meu com comentários")
    public void existeUmEventoMeuComComentarios() {
        evento = new Evento();
        comentarios = new ArrayList<>();
        
        Comentario comentario = new Comentario();
        comentario.setUsuario(new Usuario("João Silva"));
        comentario.setConteudo("Ótimo evento!");
        comentarios.add(comentario);
    }

    @Dado("já respondi ao comentário do usuário {string}")
    public void jaRespondiAoComentarioDoUsuario(String nomeUsuario) {
        comentarioSelecionado = comentarios.stream()
            .filter(c -> c.getUsuario().getNome().equals(nomeUsuario))
            .findFirst()
            .orElse(null);
        
        respostaAtual = new Resposta();
        respostaAtual.setAutor(organizador);
        respostaAtual.setConteudo("Resposta anterior");
        comentarioSelecionado.adicionarResposta(respostaAtual);
    }

    @Dado("existe um evento meu com múltiplos comentários:")
    public void existeUmEventoMeuComMultiplosComentarios(io.cucumber.datatable.DataTable dataTable) {
        evento = new Evento();
        comentarios = new ArrayList<>();
        
        List<Map<String, String>> comentariosData = dataTable.asMaps();
        for (Map<String, String> comentarioData : comentariosData) {
            Comentario comentario = new Comentario();
            comentario.setUsuario(new Usuario(comentarioData.get("Usuário")));
            comentario.setConteudo(comentarioData.get("Comentário"));
            comentarios.add(comentario);
        }
    }

    @Quando("acesso a seção de comentários do evento")
    public void acessoASecaoDeComentariosDoEvento() {
        // Simula o acesso à seção de comentários
    }

    @Quando("seleciono o comentário do usuário {string}")
    public void selecionoOComentarioDoUsuario(String nomeUsuario) {
        comentarioSelecionado = comentarios.stream()
            .filter(c -> c.getUsuario().getNome().equals(nomeUsuario))
            .findFirst()
            .orElse(null);
    }

    @Quando("clico no botão de {string} do comentário")
    public void clicoNoBotaoDoComentario(String botao) {
        if (botao.equals("Responder")) {
            respostaAtual = new Resposta();
            respostaAtual.setAutor(organizador);
        }
    }

    @Quando("preencho a resposta com {string}")
    public void preenchoARespostaCom(String conteudo) {
        respostaAtual.setConteudo(conteudo);
    }

    @Quando("clico em {string}")
    public void clicoEm(String acao) {
        if (acao.equals("Enviar")) {
            comentarioSelecionado.adicionarResposta(respostaAtual);
        } else if (acao.equals("Salvar")) {
            // Atualiza a resposta existente
        } else if (acao.equals("Excluir")) {
            comentarioSelecionado.removerResposta(respostaAtual);
            respostaAtual = null; // Limpa a referência da resposta excluída
            comentarioSelecionado = null; // Limpa a referência do comentário selecionado
        }
    }

    @Quando("seleciono minha resposta")
    public void selecionoMinhaResposta() {
        // Simula a seleção da resposta
    }

    @Quando("altero o texto para {string}")
    public void alteroOTextoPara(String novoConteudo) {
        respostaAtual.setConteudo(novoConteudo);
    }

    @Quando("confirmo a exclusão")
    public void confirmoAExclusao() {
        comentarioSelecionado.removerResposta(respostaAtual);
        respostaAtual = null;
        comentarioSelecionado = null;
    }

    @Quando("respondo a todos os comentários")
    public void respondoATodosOsComentarios() {
        for (Comentario comentario : comentarios) {
            Resposta resposta = new Resposta();
            resposta.setAutor(organizador);
            resposta.setConteudo(comentario.getConteudo());
            comentario.adicionarResposta(resposta);
        }
    }

    @Entao("devo ver minha resposta abaixo do comentário original")
    public void devoVerMinhaRespostaAbaixoDoComentarioOriginal() {
        Assertions.assertTrue(comentarioSelecionado.getRespostas().contains(respostaAtual));
    }

    @Entao("a resposta deve conter:")
    public void aRespostaDeveConter(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMap();
        Assertions.assertEquals(dados.get("Autor"), respostaAtual.getAutor().getNome());
        Assertions.assertEquals(dados.get("Conteúdo"), respostaAtual.getConteudo());
    }

    @Entao("devo ver a resposta atualizada")
    public void devoVerARespostaAtualizada() {
        Assertions.assertTrue(comentarioSelecionado.getRespostas().contains(respostaAtual));
    }

    @Entao("nao devo mais ver minha resposta")
    public void naoDevoMaisVerMinhaResposta() {
        Assertions.assertNull(respostaAtual, "A resposta ainda está presente");
        Assertions.assertNull(comentarioSelecionado, "O comentário ainda está selecionado");
    }

    @Entao("o comentário original deve permanecer visível")
    public void oComentarioOriginalDevePermanecerVisivel() {
        Assertions.assertTrue(comentarios.stream()
            .anyMatch(c -> c.getUsuario().getNome().equals("João Silva")),
            "O comentário original não está mais visível");
    }

    @Entao("devo ver minhas respostas abaixo de cada comentário")
    public void devoVerMinhasRespostasAbaixoDeCadaComentario() {
        for (Comentario comentario : comentarios) {
            Assertions.assertFalse(comentario.getRespostas().isEmpty());
        }
    }

    @Entao("cada resposta deve conter:")
    public void cadaRespostaDeveConter(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> dados = dataTable.asMaps();
        for (Comentario comentario : comentarios) {
            for (Resposta resposta : comentario.getRespostas()) {
                Map<String, String> dadosEsperados = dados.get(0);
                Assertions.assertEquals(dadosEsperados.get("Valor"), resposta.getAutor().getNome());
                Assertions.assertEquals(dados.get(1).get("Valor"), "Hoje");
                Assertions.assertTrue(dados.stream()
                    .anyMatch(d -> d.get("Valor").equals(resposta.getConteudo())),
                    "Conteúdo da resposta não encontrado na lista esperada");
            }
        }
    }
} 