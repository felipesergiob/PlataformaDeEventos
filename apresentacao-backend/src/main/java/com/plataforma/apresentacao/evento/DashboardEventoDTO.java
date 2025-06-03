package com.plataforma.apresentacao.evento;

import java.util.List;
import java.util.Map;

public class DashboardEventoDTO {
    private String nomeEvento;
    private int totalInscritos;
    private double mediaAvaliacoes;
    private int quantidadeAvaliacoes;
    private List<String> comentarios;
    private Map<String, Integer> distribuicaoNotas;
    private List<PublicacaoDTO> publicacoes;

     
    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public int getTotalInscritos() {
        return totalInscritos;
    }

    public void setTotalInscritos(int totalInscritos) {
        this.totalInscritos = totalInscritos;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public int getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }

    public void setQuantidadeAvaliacoes(int quantidadeAvaliacoes) {
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public Map<String, Integer> getDistribuicaoNotas() {
        return distribuicaoNotas;
    }

    public void setDistribuicaoNotas(Map<String, Integer> distribuicaoNotas) {
        this.distribuicaoNotas = distribuicaoNotas;
    }

    public List<PublicacaoDTO> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<PublicacaoDTO> publicacoes) {
        this.publicacoes = publicacoes;
    }
} 