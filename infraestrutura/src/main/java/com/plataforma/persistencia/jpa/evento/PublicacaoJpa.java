package com.plataforma.persistencia.jpa.evento;

import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PUBLICACAO")
public class PublicacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "CONTEUDO", columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Column(name = "FOTOS", length = 255)
    private String fotos;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventoJpa getEvento() {
        return evento;
    }

    public void setEvento(EventoJpa evento) {
        this.evento = evento;
    }

    public UsuarioJpa getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioJpa usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "PublicacaoJpa{" +
                "id=" + id +
                ", evento=" + evento.getTitulo() +
                ", usuario=" + usuario.getNome() +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
} 