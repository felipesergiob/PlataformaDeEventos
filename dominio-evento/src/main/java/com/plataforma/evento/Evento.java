package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import com.plataforma.compartilhado.UsuarioId;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalTime;
import java.time.LocalDate;

public class Evento {
    private final EventoId id;
    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private Integer inscritos;
    private UsuarioId organizador;
    private String genero;
    private BigDecimal valor;
    private Status status;

    private enum Status {
        SALVO,
        CONFIRMADO,
        CANCELADO
    }

    public Evento(EventoId  id, String nome, String descricao, LocalDateTime dataInicio,
            LocalDateTime dataFim, String local, UsuarioId organizador,
            String genero, BigDecimal valor) {
        notNull(id, "O id não pode ser nulo");
        this.id = id;

        setNome(nome);
        setDescricao(descricao);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setLocal(local);
        setOrganizador(organizador);
        setGenero(genero);
        setValor(valor);

        this.inscritos = 0;
    }

    public EventoId getId() {
        return id;
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

    public void setOrganizador(UsuarioId usuario) {
        notNull(usuario, "O usuario não pode ser nulo");
        
        this.organizador = usuario;
    }

    public String getOrganizador() {
        return organizador.toString();
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
        this.inscritos++;
    }

    public void decrementarInscritos() {
        isTrue(inscritos > 0, "Não há inscritos para decrementar");
        this.inscritos--;
    }

    public boolean jaOcorreu() {
        return LocalDateTime.now().isAfter(dataFim);
    }

    public boolean aindaNaoOcorreu() {
        return LocalDateTime.now().isBefore(dataInicio);
    }

    public List<Evento> listarPorOrganizador(UsuarioId organizador) {
        notNull(organizador, "O organizador não pode ser nulo");

        List<Evento> eventos = new ArrayList<>();

        return eventos;
    }

    public Status getStatus() {
        return status;
    }

    public List<Evento> listarPorFiltros(String genero, LocalTime horarioInicio, LocalTime horarioFim, LocalDate data, BigDecimal preco) {
        notNull(genero, "O gênero não pode ser nulo");
        notNull(horarioInicio, "O horário de início não pode ser nulo");
        notNull(horarioFim, "O horário de fim não pode ser nulo");
        notNull(data, "A data não pode ser nula");
        notNull(preco, "O preço não pode ser nulo");

        List<Evento> eventos = new ArrayList<>();

        return eventos;
    }

    public List<Evento> listarSalvos(UsuarioId id) {
        List<Evento> eventos = new ArrayList<>();

        List<Evento> eventosQueNaoPassaram = eventos.stream().filter(evento -> evento.getDataInicio().isAfter(LocalDateTime.now())).collect(Collectors.toList());

        eventosQueNaoPassaram.stream().filter(evento -> evento.getStatus() == Status.SALVO).collect(Collectors.toList());

        return eventosQueNaoPassaram;
    }

    public List<Evento> listarConfirmados(UsuarioId id) {
        List<Evento> eventos = new ArrayList<>();
        
        eventos.stream().filter(evento -> evento.getStatus() == Status.CONFIRMADO).collect(Collectors.toList());
        
        return eventos;
    }

    @Override
    public String toString() {
        return String.format("%s", nome);
    }
}