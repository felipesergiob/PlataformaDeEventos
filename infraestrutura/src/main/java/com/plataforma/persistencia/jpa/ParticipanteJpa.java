package com.plataforma.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARTICIPANTE_EVENTO")
@Getter
@Setter
public class ParticipanteJpa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "EVENTO_ID", nullable = false)
    private EventoJpa evento;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private UsuarioJpa usuario;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;
} 