package br.edu.cesar.eventos.dominio.relatorio;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.interacao.Avaliacao;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardEventoTest {

    @Test
    public void testAtualizarMetricasSemAvaliacoes() {
        // Arrange
        DashboardEvento dashboard = new DashboardEvento();
        dashboard.setEventoId(new EventoId(UUID.randomUUID()));
        dashboard.setTotalParticipantes(10);
        dashboard.setTotalInteressados(20);

        // Act
        dashboard.atualizarMetricas();

        // Assert
        assertEquals(0.0, dashboard.getMediaAvaliacoes(), 0.01);
    }

    @Test
    public void testAtualizarMetricasComAvaliacoes() {
        // Arrange
        DashboardEvento dashboard = new DashboardEvento();
        List<Avaliacao> avaliacoes = new ArrayList<>();
        
        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setNota(4);
        avaliacoes.add(avaliacao1);
        
        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setNota(5);
        avaliacoes.add(avaliacao2);
        
        dashboard.setAvaliacoes(avaliacoes);

        // Act
        dashboard.atualizarMetricas();

        // Assert
        assertEquals(4.5, dashboard.getMediaAvaliacoes(), 0.01);
    }

    @Test
    public void testAtualizarMetricasComListaVazia() {
        // Arrange
        DashboardEvento dashboard = new DashboardEvento();
        dashboard.setAvaliacoes(new ArrayList<>());

        // Act
        dashboard.atualizarMetricas();

        // Assert
        assertEquals(0.0, dashboard.getMediaAvaliacoes(), 0.01);
    }
} 