package com.plataforma.inscricao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
public class Inscricao {
    
    @Id
    @Column(columnDefinition = "uuid")
    @Getter
    private final InscricaoId id;

    private final UsuarioId usuarioId;
    private final EventoId eventoId;
    private final LocalDateTime dataInscricao;
    private final LocalDateTime dataCancelamento;
    private final String motivoCancelamento;
    private final Boolean ativo;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;

    public enum StatusInscricao {
        PENDENTE,
        CONFIRMADA,
        CANCELADA
    }

    public Inscricao(InscricaoId id, UsuarioId usuarioId, EventoId eventoId, 
                    LocalDateTime dataInscricao, LocalDateTime dataCancelamento,
                    String motivoCancelamento) {
        notNull(id, "O id não pode ser nulo");
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(dataInscricao, "A data de inscrição não pode ser nula");
        
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.dataInscricao = dataInscricao;
        this.dataCancelamento = dataCancelamento;
        this.motivoCancelamento = motivoCancelamento;
        this.ativo = dataCancelamento == null;
        this.status = StatusInscricao.PENDENTE;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public boolean estaAtiva() {
        return ativo;
    }

    public boolean foiCancelada() {
        return dataCancelamento != null;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public boolean estaConfirmada() {
        return status == StatusInscricao.CONFIRMADA;
    }

    public void confirmar() {
        if (status == StatusInscricao.CANCELADA) {
            throw new IllegalStateException("Inscrição cancelada não pode ser confirmada");
        }
        status = StatusInscricao.CONFIRMADA;
    }

    public void cancelar() {
        if (status == StatusInscricao.CONFIRMADA) {
            throw new IllegalStateException("Inscrição confirmada não pode ser cancelada");
        }
        status = StatusInscricao.CANCELADA;
    }

    @Override
    public String toString() {
        return String.format("Inscrição %s - Evento: %s", id, eventoId);
    }
} 