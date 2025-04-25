package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.datatable.DataTable;
import br.edu.cesar.eventos.dominio.interacao.ItemPerdido;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public class ReportarItemPerdidoSteps extends BaseSteps {
    private ItemPerdido itemPerdido;
    private boolean registroSucesso;
    private String mensagemErro;

    @Dado("que eu sou um participante de um evento finalizado")
    public void queEuSouUmParticipanteDeUmEventoFinalizado() {
        setupUsuarioQueParticipouDoEvento();
        setupEventoFinalizado();
    }

    @Quando("eu acessar a área de Achados e Perdidos")
    public void euAcessarAAreaDeAchadosEPerdidos() {
        itemPerdido = new ItemPerdido();
    }

    @Quando("preencher o formulário com os seguintes dados:")
    public void preencherOFormularioComOsSeguintesDados(DataTable dataTable) {
        Map<String, String> dados = dataTable.asMap(String.class, String.class);
        
        itemPerdido = new ItemPerdido();
        itemPerdido.setTipo(dados.get("tipo"));
        itemPerdido.setCor(dados.get("cor"));
        itemPerdido.setDescricao(dados.get("descrição"));
        itemPerdido.setLocalAproximado(dados.get("localAproximado"));
        itemPerdido.setDataReporte(LocalDateTime.now());
        itemPerdido.setEventoId(context.getEvento().getId());
        itemPerdido.setUsuarioId(context.getUsuario().getId());
        itemPerdido.setStatus("EM_ANALISE");
        
        try {
            validarCamposObrigatorios(itemPerdido);
            registroSucesso = true;
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            registroSucesso = false;
        }
    }

    @Quando("anexar uma foto do item")
    public void anexarUmaFotoDoItem() {
        itemPerdido.setFoto("https://exemplo.com/foto.jpg");
    }

    @Quando("tentar submeter o formulário sem preencher os campos obrigatórios")
    public void tentarSubmeterOFormularioSemPreencherOsCamposObrigatorios() {
        itemPerdido = new ItemPerdido();
        try {
            validarCamposObrigatorios(itemPerdido);
            registroSucesso = true;
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            registroSucesso = false;
        }
    }

    @Entao("o item perdido deve ser registrado com sucesso")
    public void oItemPerdidoDeveSerRegistradoComSucesso() {
        Assertions.assertTrue(registroSucesso);
        Assertions.assertNotNull(itemPerdido);
        Assertions.assertEquals("EM_ANALISE", itemPerdido.getStatus());
    }

    @Entao("devo receber uma confirmação do registro")
    public void devoReceberUmaConfirmacaoDoRegistro() {
        assert registroSucesso : "O registro do item perdido falhou";
    }

    @Entao("a foto deve ser salva junto com o registro")
    public void aFotoDeveSerSalvaJuntoComORegistro() {
        assert itemPerdido.getFoto() != null : "A foto não foi salva com o registro";
    }

    @Entao("devo receber uma mensagem de erro informando os campos obrigatórios")
    public void devoReceberUmaMensagemDeErroInformandoOsCamposObrigatorios() {
        assert !registroSucesso : "O registro não deveria ter sido bem sucedido";
        assert mensagemErro != null && !mensagemErro.isEmpty() : "A mensagem de erro não foi definida";
    }

    private void validarCamposObrigatorios(ItemPerdido item) {
        if (item.getTipo() == null || item.getTipo().isEmpty()) {
            throw new IllegalArgumentException("O tipo é obrigatório");
        }
        if (item.getDescricao() == null || item.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição é obrigatória");
        }
        if (item.getLocalAproximado() == null || item.getLocalAproximado().isEmpty()) {
            throw new IllegalArgumentException("O local é obrigatório");
        }
        if (item.getEventoId() == null) {
            throw new IllegalArgumentException("O evento é obrigatório");
        }
        if (item.getUsuarioId() == null) {
            throw new IllegalArgumentException("O usuário reportador é obrigatório");
        }
    }
}