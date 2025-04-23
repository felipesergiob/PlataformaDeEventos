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
import static org.junit.Assert.*;

public class ReportarItemPerdidoSteps {
    
    private Usuario usuario;
    private Evento evento;
    private ItemPerdido itemPerdido;
    private boolean registroSucesso;
    private boolean erroOcorreu;
    private String mensagemErro;
    
    @Dado("que eu sou um participante de um evento finalizado")
    public void queEuSouUmParticipanteDeUmEventoFinalizado() {
        usuario = new Usuario();
        usuario.setId(new UsuarioId());
        
        evento = new Evento();
        evento.setId(new EventoId());
        evento.setDataHora(LocalDateTime.now().minusDays(1)); // Evento finalizado
    }

    @Quando("eu acessar a área de Achados e Perdidos")
    public void euAcessarAAreaDeAchadosEPerdidos() {
        itemPerdido = new ItemPerdido();
        itemPerdido.setEventoId(evento.getId());
        itemPerdido.setUsuarioId(usuario.getId());
        itemPerdido.setDataReporte(LocalDateTime.now());
    }

    @Quando("preencher o formulário com os seguintes dados:")
    public void preencherOFormularioComOsSeguintesDados(DataTable dataTable) {
        Map<String, String> dados = dataTable.asMap(String.class, String.class);
        
        itemPerdido.setTipo(dados.get("tipo"));
        itemPerdido.setCor(dados.get("cor"));
        itemPerdido.setDescricao(dados.get("descrição"));
        itemPerdido.setLocalAproximado(dados.get("localAproximado"));
        
        if (itemPerdido.isValid()) {
            registroSucesso = true;
        } else {
            erroOcorreu = true;
            mensagemErro = "Campos obrigatórios não preenchidos";
        }
    }

    @Quando("anexar uma foto do item")
    public void anexarUmaFotoDoItem() {
        itemPerdido.setFoto("foto_base64_encoded");
    }

    @Quando("tentar submeter o formulário sem preencher os campos obrigatórios")
    public void tentarSubmeterOFormularioSemPreencherOsCamposObrigatorios() {
        itemPerdido.setTipo("");
        itemPerdido.setCor("");
        itemPerdido.setDescricao("");
        itemPerdido.setLocalAproximado("");
        
        if (!itemPerdido.isValid()) {
            erroOcorreu = true;
            mensagemErro = "Campos obrigatórios não preenchidos";
        }
    }

    @Então("o item perdido deve ser registrado com sucesso")
    public void oItemPerdidoDeveSerRegistradoComSucesso() {
        assertTrue("O registro do item perdido deveria ter sido bem sucedido", registroSucesso);
    }

    @Então("devo receber uma confirmação do registro")
    public void devoReceberUmaConfirmacaoDoRegistro() {
        assertTrue("Deveria ter recebido uma confirmação do registro", registroSucesso);
    }

    @Então("a foto deve ser salva junto com o registro")
    public void aFotoDeveSerSalvaJuntoComORegistro() {
        assertNotNull("A foto não deveria ser nula", itemPerdido.getFoto());
        assertEquals("A foto deveria ter sido salva", "foto_base64_encoded", itemPerdido.getFoto());
    }

    @Então("devo receber uma mensagem de erro informando os campos obrigatórios")
    public void devoReceberUmaMensagemDeErroInformandoOsCamposObrigatorios() {
        assertTrue("Deveria ter ocorrido um erro", erroOcorreu);
        assertEquals("A mensagem de erro deveria indicar campos obrigatórios", 
            "Campos obrigatórios não preenchidos", mensagemErro);
    }
} 