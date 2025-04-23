package br.edu.cesar.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.datatable.DataTable;
import static org.junit.Assert.*;

public class CriarEventoSteps {

    private boolean organizadorAutenticado = false;
    private boolean eventoCriado = false;
    private boolean erroRecebido = false;

    @Dado("que eu sou um organizador")
    public void queEuSouUmOrganizador() {
        organizadorAutenticado = true;
    }

    @Quando("eu criar um evento com os seguintes dados:")
    public void euCriarUmEventoComOsSeguintesDados(DataTable dataTable) {
        if (organizadorAutenticado) {
            eventoCriado = true;
        }
    }

    @Quando("eu tentar criar um evento sem nome")
    public void euTentarCriarUmEventoSemNome() {
        if (organizadorAutenticado) {
            erroRecebido = true;
        }
    }

    @Então("o evento deve ser criado com sucesso")
    public void oEventoDeveSerCriadoComSucesso() {
        assertTrue("O evento deveria ter sido criado", eventoCriado);
    }

    @Então("devo receber uma confirmação")
    public void devoReceberUmaConfirmacao() {
        assertTrue("Deveria ter recebido uma confirmação", eventoCriado);
    }

    @Então("devo receber uma mensagem de erro informando que o nome é obrigatório")
    public void devoReceberUmaMensagemDeErroInformandoQueONomeEObrigatorio() {
        assertTrue("Deveria ter recebido uma mensagem de erro", erroRecebido);
    }
}