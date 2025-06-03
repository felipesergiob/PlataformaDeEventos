package com.plataforma.apresentacao.usuario;

import java.util.List;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private List<EventoDTO> eventosParticipados;
    private List<EventoDTO> eventosOrganizados;
    private int totalEventosParticipados;
    private int totalEventosOrganizados;

     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<EventoDTO> getEventosParticipados() {
        return eventosParticipados;
    }

    public void setEventosParticipados(List<EventoDTO> eventosParticipados) {
        this.eventosParticipados = eventosParticipados;
    }

    public List<EventoDTO> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setEventosOrganizados(List<EventoDTO> eventosOrganizados) {
        this.eventosOrganizados = eventosOrganizados;
    }

    public int getTotalEventosParticipados() {
        return totalEventosParticipados;
    }

    public void setTotalEventosParticipados(int totalEventosParticipados) {
        this.totalEventosParticipados = totalEventosParticipados;
    }

    public int getTotalEventosOrganizados() {
        return totalEventosOrganizados;
    }

    public void setTotalEventosOrganizados(int totalEventosOrganizados) {
        this.totalEventosOrganizados = totalEventosOrganizados;
    }
} 