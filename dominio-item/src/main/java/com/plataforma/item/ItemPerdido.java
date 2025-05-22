package com.plataforma.item;

import com.plataforma.evento.EventoId;
import com.plataforma.usuario.UsuarioId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import java.time.LocalDateTime;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

@Entity
public class ItemPerdido {
    
    @Id
    @Column(columnDefinition = "uuid")
    @Getter
    private final ItemPerdidoId id;

    @Getter
    private final EventoId evento;

    @Getter
    private final UsuarioId registrante;

    private String descricao;
    private String localEncontrado;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataRecuperacao;
    private UsuarioId recuperadoPor;
    private String observacoes;
    
    @Enumerated(EnumType.STRING)
    private StatusItem status;

    public enum StatusItem {
        PENDENTE,
        RECUPERADO,
        ARQUIVADO
    }

    public ItemPerdido(ItemPerdidoId id, EventoId evento, UsuarioId registrante, 
                      String descricao, String localEncontrado) {
        notNull(id, "O id não pode ser nulo");
        notNull(evento, "O evento não pode ser nulo");
        notNull(registrante, "O registrante não pode ser nulo");
        
        this.id = id;
        this.evento = evento;
        this.registrante = registrante;
        
        setDescricao(descricao);
        setLocalEncontrado(localEncontrado);
        
        this.dataRegistro = LocalDateTime.now();
        this.status = StatusItem.PENDENTE;
    }

    public void setDescricao(String descricao) {
        notNull(descricao, "A descrição não pode ser nula");
        notBlank(descricao, "A descrição não pode estar em branco");
        isTrue(descricao.length() >= 10, "A descrição deve ter pelo menos 10 caracteres");
        isTrue(descricao.length() <= 500, "A descrição deve ter no máximo 500 caracteres");
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setLocalEncontrado(String localEncontrado) {
        notNull(localEncontrado, "O local encontrado não pode ser nulo");
        notBlank(localEncontrado, "O local encontrado não pode estar em branco");
        isTrue(localEncontrado.length() <= 200, "O local encontrado deve ter no máximo 200 caracteres");
        this.localEncontrado = localEncontrado;
    }

    public String getLocalEncontrado() {
        return localEncontrado;
    }

    public void marcarComoRecuperado(UsuarioId recuperadoPor) {
        notNull(recuperadoPor, "O usuário que recuperou não pode ser nulo");
        if (status != StatusItem.PENDENTE) {
            throw new IllegalStateException("Apenas itens pendentes podem ser marcados como recuperados");
        }
        this.recuperadoPor = recuperadoPor;
        this.dataRecuperacao = LocalDateTime.now();
        this.status = StatusItem.RECUPERADO;
    }

    public void arquivar() {
        if (status == StatusItem.ARQUIVADO) {
            throw new IllegalStateException("O item já está arquivado");
        }
        this.status = StatusItem.ARQUIVADO;
    }

    public void adicionarObservacao(String observacao) {
        notNull(observacao, "A observação não pode ser nula");
        notBlank(observacao, "A observação não pode estar em branco");
        isTrue(observacao.length() <= 1000, "A observação deve ter no máximo 1000 caracteres");
        this.observacoes = observacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public LocalDateTime getDataRecuperacao() {
        return dataRecuperacao;
    }

    public UsuarioId getRecuperadoPor() {
        return recuperadoPor;
    }

    public StatusItem getStatus() {
        return status;
    }

    public boolean estaRecuperado() {
        return status == StatusItem.RECUPERADO;
    }

    public boolean estaPendente() {
        return status == StatusItem.PENDENTE;
    }

    public boolean estaArquivado() {
        return status == StatusItem.ARQUIVADO;
    }

    @Override
    public String toString() {
        return String.format("Item %s - %s (%s)", 
            id.getCodigo(), 
            descricao, 
            status);
    }
} 