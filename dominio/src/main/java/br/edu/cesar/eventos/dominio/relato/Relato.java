package br.edu.cesar.eventos.dominio.relato;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Relato {
    private String titulo;
    private String conteudo;
    private Usuario autor;
    private LocalDateTime dataCriacao;
    private List<Foto> fotos;
    private String eventoId;

    public Relato() {
        this.fotos = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void adicionarFoto(Foto foto) {
        this.fotos.add(foto);
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public boolean isValid() {
        return titulo != null && !titulo.isEmpty() && 
               conteudo != null && !conteudo.isEmpty() &&
               autor != null && eventoId != null;
    }
}