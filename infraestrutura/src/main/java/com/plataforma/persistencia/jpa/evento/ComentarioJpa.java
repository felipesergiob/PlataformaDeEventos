package com.plataforma.persistencia.jpa.evento;

import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMENTARIO")
public class ComentarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "COMENTARIO", columnDefinition = "TEXT", nullable = false)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "COMENTARIO_PAI_ID")
    private ComentarioJpa comentarioPai;

    @OneToMany(mappedBy = "comentarioPai")
    private List<ComentarioJpa> respostas = new ArrayList<>();

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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ComentarioJpa getComentarioPai() {
        return comentarioPai;
    }

    public void setComentarioPai(ComentarioJpa comentarioPai) {
        this.comentarioPai = comentarioPai;
    }

    public List<ComentarioJpa> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<ComentarioJpa> respostas) {
        this.respostas = respostas;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "ComentarioJpa{" +
                "id=" + id +
                ", evento=" + evento.getTitulo() +
                ", usuario=" + usuario.getNome() +
                ", comentario='" + comentario + '\'' +
                '}';
    }
} 