package com.plataforma.avaliacao.resposta;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.AvaliacaoId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

@Entity
public class RespostaAvaliacao {
    
    @Id
    @Column(columnDefinition = "uuid")
    @Getter
    private final RespostaAvaliacaoId id;

    private final UsuarioId usuarioId;
    private final AvaliacaoId avaliacaoId;
    private final LocalDateTime dataResposta;
    private final String comentario;
    private Boolean ativo;
    private LocalDateTime dataAtualizacao;
    private String comentarioModerado;

    public RespostaAvaliacao(RespostaAvaliacaoId id, UsuarioId usuarioId, AvaliacaoId avaliacaoId, 
                           LocalDateTime dataResposta, String comentario) {
        notNull(id, "O id não pode ser nulo");
        notNull(usuarioId, "O id do usuário não pode ser nulo");
        notNull(avaliacaoId, "O id da avaliação não pode ser nulo");
        notNull(dataResposta, "A data da resposta não pode ser nula");
        notNull(comentario, "O comentário não pode ser nulo");
        
        this.id = id;
        this.usuarioId = usuarioId;
        this.avaliacaoId = avaliacaoId;
        this.dataResposta = dataResposta;
        this.comentario = comentario;
        this.ativo = true;
        this.dataAtualizacao = dataResposta;
        this.comentarioModerado = null;
    }

    public RespostaAvaliacaoId getId() {
        return id;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public AvaliacaoId getAvaliacaoId() {
        return avaliacaoId;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public String getComentario() {
        return comentario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public String getComentarioModerado() {
        return comentarioModerado;
    }

    public boolean estaAtiva() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void ativar() {
        this.ativo = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void moderarComentario(String comentarioModerado) {
        notNull(comentarioModerado, "O comentário moderado não pode ser nulo");
        this.comentarioModerado = comentarioModerado;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public boolean temComentarioModerado() {
        return comentarioModerado != null;
    }

    public boolean comentarioPrecisaModeracao() {
        return !temComentarioModerado() && 
               (comentario.toLowerCase().contains("palavrão") || 
                comentario.toLowerCase().contains("ofensa"));
    }

    public boolean podeSerEditada() {
        return LocalDateTime.now().isBefore(dataResposta.plusDays(7));
    }

    @Override
    public String toString() {
        return String.format("Resposta %s - Avaliação: %s", id, avaliacaoId);
    }
} 