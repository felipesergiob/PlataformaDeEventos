package com.plataforma.dominio.evento;

import com.plataforma.avaliacao.Avaliacao;
import com.plataforma.avaliacao.AvaliacaoRepository;
import com.plataforma.avaliacao.AvaliacaoService;
import com.plataforma.compartilhado.EventoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class VisualizarResumoAvaliacoesSteps {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private Map<String, Object> resumoAvaliacoes;
    private List<Avaliacao> avaliacoes = new ArrayList<>();
    private EventoId eventoId;

    @Dado("o organizador {string} possui os seguintes eventos passados:")
    public void o_organizador_possui_os_seguintes_eventos_passados(String organizador, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        eventoId = EventoId.de(1);

        for (Map<String, String> row : rows) {
            Avaliacao avaliacao = Mockito.mock(Avaliacao.class);
            Mockito.when(avaliacao.getNota()).thenReturn(Integer.parseInt(row.get("mediaAvaliacao").split("\\.")[0]));
            avaliacoes.add(avaliacao);
        }

        Mockito.when(avaliacaoRepository.listarPorEvento(eventoId)).thenReturn(avaliacoes);
        Mockito.when(avaliacaoRepository.listarNotasEvento(eventoId)).thenReturn(avaliacoes);
        Mockito.when(avaliacaoRepository.contarParticipantesConfirmados(eventoId))
               .thenReturn(Integer.parseInt(rows.get(0).get("participantes")));
    }

    @Quando("eu acessar o perfil público do organizador {string}")
    public void eu_acessar_o_perfil_publico_do_organizador(String organizador) {
        resumoAvaliacoes = avaliacaoService.visualizarResumoAvaliacoes(eventoId);
    }

    @Entao("devo ver a lista de eventos passados organizados por ele")
    public void devo_ver_a_lista_de_eventos_passados_organizados_por_ele() {
        Assert.assertNotNull("O resumo não deve ser nulo", resumoAvaliacoes);
        Assert.assertTrue("Deve conter a lista de avaliações", resumoAvaliacoes.containsKey("avaliacoes"));
        Assert.assertTrue("Deve conter a média das notas", resumoAvaliacoes.containsKey("mediaNotas"));
        Assert.assertTrue("Deve conter a quantidade de participantes", resumoAvaliacoes.containsKey("quantidadeParticipantes"));
    }

    @Entao("para cada evento devo ver a média de avaliação, comentários dos participantes e quantidade de participantes confirmados")
    public void para_cada_evento_devo_ver_a_media_de_avaliacao_comentarios_dos_participantes_e_quantidade_de_participantes_confirmados() {
        List<Avaliacao> avaliacoesRetornadas = (List<Avaliacao>) resumoAvaliacoes.get("avaliacoes");
        double mediaNotas = (double) resumoAvaliacoes.get("mediaNotas");
        int quantidadeParticipantes = (int) resumoAvaliacoes.get("quantidadeParticipantes");

        Assert.assertEquals("A quantidade de avaliações deve ser igual", avaliacoes.size(), avaliacoesRetornadas.size());
        Assert.assertTrue("A média das notas deve ser maior que zero", mediaNotas > 0);
        Assert.assertTrue("A quantidade de participantes deve ser maior que zero", quantidadeParticipantes > 0);
    }
}
