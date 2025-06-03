package com.plataforma.persistencia.jpa.evento;

import com.plataforma.persistencia.jpa.usuario.UsuarioJpa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARTICIPANTE_EVENTO")
public class ParticipanteEventoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ParticipanteEventoJpa{" +
                "id=" + id +
                ", evento=" + evento.getTitulo() +
                ", usuario=" + usuario.getNome() +
                ", status='" + status + '\'' +
                '}';
    }
} 