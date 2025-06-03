package com.plataforma.persistencia.jpa.evento;

import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AVALIACAO")
public class AvaliacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "NOTA", nullable = false)
    private Integer nota;

    @Column(name = "COMENTARIO", columnDefinition = "TEXT")
    private String comentario;

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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "AvaliacaoJpa{" +
                "id=" + id +
                ", evento=" + evento.getTitulo() +
                ", usuario=" + usuario.getNome() +
                ", nota=" + nota +
                '}';
    }
} 