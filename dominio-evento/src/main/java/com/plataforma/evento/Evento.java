package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import lombok.Getter;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

public class Evento {
    
    @Getter
    private final EventoId id;

    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private Integer capacidade;
    private Integer inscritos;
    private String organizador;
    private String categoria;
    private String genero;
    private BigDecimal valor;
    private StatusEvento status;
    private Boolean ativo;

    public enum StatusEvento {
        AGENDADO,
        EM_ANDAMENTO,
        FINALIZADO,
        CANCELADO
    }

    public Evento(EventoId id, String nome, String descricao, LocalDateTime dataInicio, 
                 LocalDateTime dataFim, String local, Integer capacidade, String organizador,
                 String categoria, String genero, BigDecimal valor) {
        notNull(id, "O id não pode ser nulo");
        this.id = id;
        
        setNome(nome);
        setDescricao(descricao);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setLocal(local);
        setCapacidade(capacidade);
        setOrganizador(organizador);
        setCategoria(categoria);
        setGenero(genero);
        setValor(valor);
        
        this.inscritos = 0;
        this.status = StatusEvento.AGENDADO;
        this.ativo = true;
    }

    public void setNome(String nome) {
        notNull(nome, "O nome não pode ser nulo");
        notBlank(nome, "O nome não pode estar em branco");
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDescricao(String descricao) {
        notNull(descricao, "A descrição não pode ser nula");
        notBlank(descricao, "A descrição não pode estar em branco");
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        notNull(dataInicio, "A data de início não pode ser nula");
        if (dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim");
        }
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataFim(LocalDateTime dataFim) {
        notNull(dataFim, "A data de fim não pode ser nula");
        if (dataInicio != null && dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setLocal(String local) {
        notNull(local, "O local não pode ser nulo");
        notBlank(local, "O local não pode estar em branco");
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setCapacidade(Integer capacidade) {
        notNull(capacidade, "A capacidade não pode ser nula");
        isTrue(capacidade > 0, "A capacidade deve ser maior que zero");
        this.capacidade = capacidade;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setOrganizador(String organizador) {
        notNull(organizador, "O organizador não pode ser nulo");
        notBlank(organizador, "O organizador não pode estar em branco");
        this.organizador = organizador;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setCategoria(String categoria) {
        notNull(categoria, "A categoria não pode ser nula");
        notBlank(categoria, "A categoria não pode estar em branco");
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setGenero(String genero) {
        notNull(genero, "O gênero não pode ser nulo");
        notBlank(genero, "O gênero não pode estar em branco");
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setValor(BigDecimal valor) {
        notNull(valor, "O valor não pode ser nulo");
        isTrue(valor.compareTo(BigDecimal.ZERO) >= 0, "O valor não pode ser negativo");
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public void incrementarInscritos() {
        isTrue(inscritos < capacidade, "Evento já atingiu capacidade máxima");
        this.inscritos++;
        atualizarStatus();
    }

    public void decrementarInscritos() {
        isTrue(inscritos > 0, "Não há inscritos para decrementar");
        this.inscritos--;
        atualizarStatus();
    }

    public StatusEvento getStatus() {
        return status;
    }

    private void atualizarStatus() {
        if (!estaAtivo()) {
            status = StatusEvento.CANCELADO;
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(dataInicio)) {
            status = StatusEvento.AGENDADO;
        } else if (agora.isAfter(dataFim)) {
            status = StatusEvento.FINALIZADO;
        } else {
            status = StatusEvento.EM_ANDAMENTO;
        }
    }

    public boolean temVagasDisponiveis() {
        return inscritos < capacidade;
    }

    public int vagasDisponiveis() {
        return capacidade - inscritos;
    }

    public boolean estaLotado() {
        return inscritos >= capacidade;
    }

    public boolean podeSerCancelado() {
        return status != StatusEvento.FINALIZADO && 
               status != StatusEvento.CANCELADO;
    }

    public void cancelar() {
        if (!podeSerCancelado()) {
            throw new IllegalStateException("Evento não pode ser cancelado");
        }
        status = StatusEvento.CANCELADO;
        ativo = false;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public boolean estaOcorrendo() {
        if (!estaAtivo()) return false;
        LocalDateTime agora = LocalDateTime.now();
        return !agora.isBefore(dataInicio) && !agora.isAfter(dataFim);
    }

    public boolean jaOcorreu() {
        if (!estaAtivo()) return false;
        return LocalDateTime.now().isAfter(dataFim);
    }

    public boolean aindaNaoOcorreu() {
        if (!estaAtivo()) return false;
        return LocalDateTime.now().isBefore(dataInicio);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", nome, status);
    }
} 