package com.plataforma.apresentacao.evento;

import java.util.List;

public class PerfilOrganizadorDTO {
    private String id;
    private String nome;
    private String email;
    private List<EventoDTO> eventosPassados;
    private List<AvaliacaoDTO> avaliacoes;
    private double mediaAvaliacoes;
    private int totalEventos;
    private int totalParticipantes;

     
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EventoDTO> getEventosPassados() {
        return eventosPassados;
    }

    public void setEventosPassados(List<EventoDTO> eventosPassados) {
        this.eventosPassados = eventosPassados;
    }

    public List<AvaliacaoDTO> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoDTO> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public int getTotalEventos() {
        return totalEventos;
    }

    public void setTotalEventos(int totalEventos) {
        this.totalEventos = totalEventos;
    }

    public int getTotalParticipantes() {
        return totalParticipantes;
    }

    public void setTotalParticipantes(int totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }
} 