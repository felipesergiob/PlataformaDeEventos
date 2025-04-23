package br.edu.cesar.eventos.dominio.relatorio;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.interacao.Avaliacao;
import br.edu.cesar.eventos.dominio.interacao.ItemPerdido;
import br.edu.cesar.eventos.dominio.relato.Relato;
import java.util.List;

public class DashboardEvento {
    private EventoId eventoId;
    private int totalParticipantes;
    private int totalInteressados;
    private double mediaAvaliacoes;
    private List<Avaliacao> avaliacoes;
    private List<Relato> relatos;
    private List<ItemPerdido> itensPerdidos;

    public EventoId getEventoId() {
        return eventoId;
    }

    public void setEventoId(EventoId eventoId) {
        this.eventoId = eventoId;
    }

    public int getTotalParticipantes() {
        return totalParticipantes;
    }

    public void setTotalParticipantes(int totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }

    public int getTotalInteressados() {
        return totalInteressados;
    }

    public void setTotalInteressados(int totalInteressados) {
        this.totalInteressados = totalInteressados;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Relato> getRelatos() {
        return relatos;
    }

    public void setRelatos(List<Relato> relatos) {
        this.relatos = relatos;
    }

    public List<ItemPerdido> getItensPerdidos() {
        return itensPerdidos;
    }

    public void setItensPerdidos(List<ItemPerdido> itensPerdidos) {
        this.itensPerdidos = itensPerdidos;
    }

    public void atualizarMetricas() {
        if (avaliacoes != null && !avaliacoes.isEmpty()) {
            mediaAvaliacoes = avaliacoes.stream()
                .mapToInt(a -> a.getNota())
                .average()
                .orElse(0.0);
        }
    }

    public void gerarRelatorio() {
        atualizarMetricas();
        // Aqui seria implementada a lógica para gerar o relatório completo
    }
}