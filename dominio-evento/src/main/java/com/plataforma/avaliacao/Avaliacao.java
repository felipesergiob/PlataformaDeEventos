package com.plataforma.avaliacao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.AvaliacaoId;
import com.plataforma.avaliacao.resposta.RespostaAvaliacao;
import com.plataforma.avaliacao.resposta.RespostaAvaliacaoId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

@Entity
public class Avaliacao {
    
    @Id
    @Column(columnDefinition = "uuid")
    @Getter
    private final AvaliacaoId id;

    private final UsuarioId usuarioId;
    private final EventoId eventoId;
    private final String nomeUsuario;
    private final Integer nota;
    private final String comentario;
    private final LocalDateTime dataAvaliacao;
    private final LocalDateTime dataAtualizacao;
    private final Boolean ativo;
    private final Boolean comentarioModerado;
    private final LocalDateTime dataModeracao;
    private final List<RespostaAvaliacao> respostas;

    public Avaliacao(AvaliacaoId id, UsuarioId usuarioId, EventoId eventoId, String nomeUsuario, 
                    Integer nota, String comentario, LocalDateTime dataAvaliacao,
                    LocalDateTime dataAtualizacao, Boolean ativo, Boolean comentarioModerado,
                    LocalDateTime dataModeracao) {
        notNull(id, "O id não pode ser nulo");
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(eventoId, "O id do evento não pode ser nulo");
        notNull(dataAvaliacao, "A data da avaliação não pode ser nula");
        notNull(nota, "A nota não pode ser nula");
        
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.nomeUsuario = requireNonNull(nomeUsuario, "Nome do usuário não pode ser nulo");
        this.nota = nota;
        this.comentario = requireNonNull(comentario, "Comentário não pode ser nulo");
        this.dataAvaliacao = dataAvaliacao;
        this.dataAtualizacao = dataAtualizacao;
        this.ativo = ativo;
        this.comentarioModerado = comentarioModerado;
        this.dataModeracao = dataModeracao;
        this.respostas = new ArrayList<>();
    }

    public AvaliacaoId getId() {
        return id;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public Integer getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean estaAtiva() {
        return isAtivo();
    }

    public boolean isComentarioModerado() {
        return comentarioModerado;
    }

    public boolean getComentarioModerado() {
        return isComentarioModerado();
    }

    public LocalDateTime getDataModeracao() {
        return dataModeracao;
    }

    public List<RespostaAvaliacao> getRespostas() {
        return new ArrayList<>(respostas);
    }

    public void adicionarResposta(RespostaAvaliacao resposta) {
        requireNonNull(resposta, "Resposta não pode ser nula");
        respostas.add(resposta);
    }

    public void removerResposta(RespostaAvaliacaoId respostaId) {
        requireNonNull(respostaId, "ID da resposta não pode ser nulo");
        respostas.removeIf(r -> r.getId().equals(respostaId));
    }

    public boolean comentarioPrecisaModeracao() {
        return !isComentarioModerado() && 
               (comentario.toLowerCase().contains("palavrão") || 
                comentario.toLowerCase().contains("ofensa"));
    }

    public boolean temComentarioModerado() {
        return isComentarioModerado();
    }

    @Override
    public String toString() {
        return String.format("Avaliação [ID: %s, Evento: %s, Usuário: %s, Nota: %d, Data: %s]",
            id, eventoId, usuarioId, nota, dataAvaliacao);
    }
} 