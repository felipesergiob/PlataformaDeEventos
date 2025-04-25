package br.edu.cesar.eventos.dominio.relato;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;

public class Comentario {
    private String id;
    private String conteudo;
    private UsuarioId autorId;
    private LocalDateTime dataCriacao;
    private boolean moderado;
    private String statusModeracao;

    public Comentario() {
        this.dataCriacao = LocalDateTime.now();
        this.moderado = false;
        this.statusModeracao = "PENDENTE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public UsuarioId getAutorId() {
        return autorId;
    }

    public void setAutorId(UsuarioId autorId) {
        this.autorId = autorId;
    }
    
    @Deprecated
    public Usuario getAutor() {
        return null;
    }

    @Deprecated
    public void setAutor(Usuario autor) {
        if (autor != null) {
            this.autorId = autor.getId();
        }
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isModerado() {
        return moderado;
    }

    public String getStatusModeracao() {
        return statusModeracao;
    }

    public void moderar(String status) {
        this.moderado = true;
        this.statusModeracao = status;
    }

    public boolean isValid() {
        return conteudo != null && !conteudo.isEmpty() && autorId != null;
    }
} 