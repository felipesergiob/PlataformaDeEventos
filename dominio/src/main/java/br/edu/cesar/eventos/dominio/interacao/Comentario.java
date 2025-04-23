package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comentario {
    private Usuario usuario;
    private String conteudo;
    private LocalDateTime data;
    private List<Resposta> respostas;

    public Comentario() {
        this.respostas = new ArrayList<>();
        this.data = LocalDateTime.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public void adicionarResposta(Resposta resposta) {
        respostas.add(resposta);
    }

    public void removerResposta(Resposta resposta) {
        respostas.remove(resposta);
    }
}