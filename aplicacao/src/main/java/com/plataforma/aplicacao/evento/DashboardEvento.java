package com.plataforma.aplicacao.evento;

import java.util.List;
import java.util.Map;

public class DashboardEvento {
    private String nomeEvento;
    private int totalInscritos;
    private double mediaAvaliacoes;
    private int quantidadeAvaliacoes;
    private List<String> comentarios;
    private Map<String, Integer> distribuicaoNotas;
    private List<Publicacao> publicacoes;

     
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

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }
} 