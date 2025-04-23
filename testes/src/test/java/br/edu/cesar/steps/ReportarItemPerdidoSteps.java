package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.datatable.DataTable;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.interacao.ItemPerdido;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ReportarItemPerdidoSteps {
    
    private Usuario usuario;
    private Evento evento;
    private ItemPerdido itemPerdido;
    private boolean registroSucesso;
    private String mensagemErro;
    
    @Dado("que sou um usuário logado na plataforma")
    public void queSouUmUsuarioLogadoNaPlataforma() {
        // Implementação do login do usuário
    }

    @Dado("tenho um evento encerrado que criei")
    public void tenhoUmEventoEncerradoQueCriei() {
        evento = new Evento();
        evento.setId(new EventoId("evento123"));
        evento.setCriadorId(new UsuarioId("usuario123"));
        evento.setStatus("ENCERRADO");
    }

    @Dado("que eu sou um participante de um evento finalizado")
    public void queEuSouUmParticipanteDeUmEventoFinalizado() {
        // Simula um usuário participante de um evento finalizado
        itemPerdido = new ItemPerdido();
        itemPerdido.setEventoId(new EventoId("123"));
        itemPerdido.setUsuarioId(new UsuarioId("456"));
    }

    @Quando("preencho o formulário de item perdido com os seguintes dados:")
    public void preenchoOFormularioDeItemPerdidoComOsSeguintesDados(DataTable dataTable) {
        itemPerdido = new ItemPerdido();
        itemPerdido.setEventoId(evento.getId());
        itemPerdido.setUsuarioId(new UsuarioId("usuario123"));
        itemPerdido.setTipo(dataTable.cell(1, 0));
        itemPerdido.setCor(dataTable.cell(1, 1));
        itemPerdido.setDescricao(dataTable.cell(1, 2));
        itemPerdido.setLocalAproximado(dataTable.cell(1, 3));
        registroSucesso = itemPerdido.isValid();
    }

    @Quando("anexo uma foto do item")
    public void anexoUmaFotoDoItem() {
        itemPerdido.setFoto("caminho/para/foto.jpg");
    }

    @Quando("não preencho os campos obrigatórios")
    public void naoPreenchoOsCamposObrigatorios() {
        itemPerdido = new ItemPerdido();
        registroSucesso = itemPerdido.isValid();
        if (!registroSucesso) {
            mensagemErro = "Todos os campos são obrigatórios";
        }
    }

    @Quando("eu acessar a área de Achados e Perdidos")
    public void euAcessarAAreaDeAchadosEPerdidos() {
        // Simula o acesso à área de Achados e Perdidos
    }

    @Quando("preencher o formulário com os seguintes dados:")
    public void preencherOFormularioComOsSeguintesDados(Map<String, String> dados) {
        itemPerdido.setTipo(dados.get("tipo"));
        itemPerdido.setCor(dados.get("cor"));
        itemPerdido.setDescricao(dados.get("descrição"));
        itemPerdido.setLocalAproximado(dados.get("localAproximado"));
        
        registroSucesso = itemPerdido.isValid();
    }

    @Quando("anexar uma foto do item")
    public void anexarUmaFotoDoItem() {
        itemPerdido.setFoto("foto.jpg");
    }

    @Quando("tentar submeter o formulário sem preencher os campos obrigatórios")
    public void tentarSubmeterOFormularioSemPreencherOsCamposObrigatorios() {
        registroSucesso = false;
        mensagemErro = "Por favor, preencha todos os campos obrigatórios";
    }

    @Então("o item perdido deve ser registrado com sucesso")
    public void oItemPerdidoDeveSerRegistradoComSucesso() {
        assertTrue(registroSucesso, "O item perdido deveria ter sido registrado com sucesso");
    }

    @Então("devo ver uma mensagem de erro informando que os campos são obrigatórios")
    public void devoVerUmaMensagemDeErroInformandoQueOsCamposSaoObrigatorios() {
        assertFalse(registroSucesso, "O registro deveria ter falhado");
        assertEquals("Todos os campos são obrigatórios", mensagemErro);
    }

    @Então("devo receber uma confirmação do registro")
    public void devoReceberUmaConfirmacaoDoRegistro() {
        assertTrue(registroSucesso, "Deve receber uma confirmação do registro");
    }

    @Então("a foto deve ser salva junto com o registro")
    public void aFotoDeveSerSalvaJuntoComORegistro() {
        assertNotNull(itemPerdido.getFoto(), "A foto deve ser salva junto com o registro");
    }

    @Então("devo receber uma mensagem de erro informando os campos obrigatórios")
    public void devoReceberUmaMensagemDeErroInformandoOsCamposObrigatorios() {
        assertFalse(registroSucesso, "O registro não deve ser bem sucedido");
        assertNotNull(mensagemErro, "Deve receber uma mensagem de erro");
    }
} 