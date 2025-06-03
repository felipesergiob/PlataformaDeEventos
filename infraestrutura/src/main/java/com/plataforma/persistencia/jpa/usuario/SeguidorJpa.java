package com.plataforma.persistencia.jpa.usuario;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SEGUIDOR")
public class SeguidorJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SEGUIDOR_ID", nullable = false)
    private UsuarioJpa seguidor;

    @ManyToOne
    @JoinColumn(name = "SEGUIDO_ID", nullable = false)
    private UsuarioJpa seguido;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioJpa getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(UsuarioJpa seguidor) {
        this.seguidor = seguidor;
    }

    public UsuarioJpa getSeguido() {
        return seguido;
    }

    public void setSeguido(UsuarioJpa seguido) {
        this.seguido = seguido;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "SeguidorJpa{" +
                "id=" + id +
                ", seguidor=" + seguidor.getNome() +
                ", seguido=" + seguido.getNome() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
} 