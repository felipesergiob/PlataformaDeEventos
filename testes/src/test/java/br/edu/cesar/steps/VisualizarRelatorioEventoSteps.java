package br.edu.cesar.steps;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.Locale;

public class VisualizarRelatorioEventoSteps {
    private Evento evento;
    private Map<String, Object> relatorio;
    private String periodoSelecionado;

    @Dado("que sou um usuário logado na plataforma e criador de eventos")
    public void queSouUmUsuarioLogadoNaPlataformaECriadorDeEventos() {
        // Simula um usuário logado
    }

    @Dado("tenho um evento encerrado que criei para visualizar relatório")
    public void tenhoUmEventoEncerradoQueCrieiParaVisualizarRelatorio() {
        evento = new Evento();
        evento.setId(new EventoId("123"));
        evento.setCriadorId(new UsuarioId("456"));
        evento.setStatus("ENCERRADO");
        evento.setTotalConfirmacoes(50);
        evento.setTotalTalvez(20);
        evento.setTotalAvaliacoes(40);
        evento.setMediaNotas(4.5);
        evento.setTotalComentarios(30);
    }

    @Dado("o evento não recebeu avaliações")
    public void oEventoNaoRecebeuAvaliacoes() {
        evento.setTotalAvaliacoes(0);
        evento.setMediaNotas(0.0);
        evento.setTotalConfirmacoes(30);
        evento.setTotalTalvez(10);
        evento.setTotalComentarios(5);
    }

    @Quando("acesso a página de relatórios do evento")
    public void acessoAPaginaDeRelatoriosDoEvento() {
        relatorio = evento.gerarRelatorio();
    }

    @Quando("seleciono o período {string}")
    public void selecionoOPeriodo(String periodo) {
        periodoSelecionado = periodo;
        relatorio = evento.gerarRelatorio(periodo);
    }

    @Entao("devo ver as seguintes informações:")
    public void devoVerAsSeguintesInformacoes(Map<String, String> metricasEsperadas) {
        Assertions.assertNotNull(relatorio, "O relatório não deve ser nulo");
        
        metricasEsperadas.forEach((chave, valorEsperado) -> {
            Object valorAtual = relatorio.get(chave);
            Assertions.assertNotNull(valorAtual, "A métrica " + chave + " não deve ser nula");
            
            // Formata o valor atual para String, removendo zeros decimais desnecessários
            String valorAtualFormatado = formatarValor(valorAtual);
            Assertions.assertEquals(valorEsperado, valorAtualFormatado, 
                "Valor incorreto para a métrica " + chave);
        });
    }

    private String formatarValor(Object valor) {
        if (valor instanceof Double) {
            double d = (Double) valor;
            if (d == (long) d) {
                return String.format(Locale.US, "%d", (long) d);
            }
            return String.format(Locale.US, "%.1f", d);
        }
        return String.valueOf(valor);
    }

    @Entao("devo ver as métricas filtradas para o período selecionado")
    public void devoVerAsMetricasFiltradasParaOPeriodoSelecionado() {
        Assertions.assertNotNull(relatorio, "O relatório não deve ser nulo");
        Assertions.assertTrue(relatorio.containsKey("periodo"), 
            "O relatório deve conter o período selecionado");
        Assertions.assertEquals(periodoSelecionado, relatorio.get("periodo"),
            "O período no relatório deve corresponder ao selecionado");
    }
} 