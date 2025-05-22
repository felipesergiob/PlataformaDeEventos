package com.plataforma.historico;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import lombok.Getter;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;

public class Historico {
    
    @Getter
    private final HistoricoId id;

    private final EventoId eventoId;
    private final String nomeEvento;
    private final LocalDateTime dataEvento;
    private final LocalDateTime dataParticipacao;
    private final AvaliacaoId avaliacaoId;
    private final Integer nota;
    private final String comentario;
    private Boolean ativo;

    public Historico(HistoricoId id, EventoId eventoId, String nomeEvento, 
                    LocalDateTime dataEvento, LocalDateTime dataParticipacao,
                    AvaliacaoId avaliacaoId, Integer nota, String comentario) {
        notNull(id, "O id não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(nomeEvento, "O nome do evento não pode ser nulo");
        notNull(dataEvento, "A data do evento não pode ser nula");
        notNull(dataParticipacao, "A data de participação não pode ser nula");
        
        this.id = id;
        this.eventoId = eventoId;
        this.nomeEvento = nomeEvento;
        this.dataEvento = dataEvento;
        this.dataParticipacao = dataParticipacao;
        this.avaliacaoId = avaliacaoId;
        this.nota = nota;
        this.comentario = comentario;
        this.ativo = true;
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public LocalDateTime getDataParticipacao() {
        return dataParticipacao;
    }

    public AvaliacaoId getAvaliacaoId() {
        return avaliacaoId;
    }

    public Integer getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public boolean temAvaliacao() {
        return avaliacaoId != null;
    }

    public boolean temNotaAlta() {
        return nota != null && nota >= 4;
    }

    public boolean temNotaBaixa() {
        return nota != null && nota <= 2;
    }

    @Override
    public String toString() {
        return String.format("%s - Participação: %s", nomeEvento, dataParticipacao);
    }
}