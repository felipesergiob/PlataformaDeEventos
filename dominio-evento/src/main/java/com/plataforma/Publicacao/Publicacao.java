package com.plataforma.Publicacao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.PublicacaoId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Publicacao {
    private final PublicacaoId id;
    private final UsuarioId autorId;
    private final EventoId eventoId;
    private final String conteudo;
    private final List<String> fotos;
    private final LocalDateTime dataCriacao;

    public Publicacao(PublicacaoId id, UsuarioId autorId, EventoId eventoId, String conteudo) {
        this.id = id;
        this.autorId = autorId;
        this.eventoId = eventoId;
        this.conteudo = conteudo;
        this.fotos = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
    }

    public PublicacaoId getId() {
        return id;
    }

    public UsuarioId getAutorId() {
        return autorId;
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public List<String> getFotos() {
        return new ArrayList<>(fotos);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void adicionarFoto(String urlFoto) {
        this.fotos.add(urlFoto);
    }
}
