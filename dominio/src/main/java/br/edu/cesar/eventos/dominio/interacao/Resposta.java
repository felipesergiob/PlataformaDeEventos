package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.time.LocalDateTime;

public class Resposta {
    private Usuario autor;
    private String conteudo;
    private LocalDateTime data;

    public Resposta() {
        this.data = LocalDateTime.now();
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
} 