package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface EventoRepository {
    void salvar(Evento evento);
    Evento obter(EventoId id);
    List<Evento> listarTodos();
    List<Evento> listarAtivos();
    List<Evento> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<Evento> listarPorCategoria(String categoria);
    List<Evento> listarPorGenero(String genero);
    List<Evento> listarPorOrganizador(String organizador);
    List<Evento> listarPorValor(BigDecimal valorMinimo, BigDecimal valorMaximo);
    List<Evento> listarPorStatus(Evento.StatusEvento status);
    List<Evento> listarPorHorario(LocalTime horarioInicio, LocalTime horarioFim);
    List<Evento> listarPorData(LocalDate data);
    List<Evento> listarPorPreco(BigDecimal preco);
    List<Evento> listarPorNumeroInscritos(int limite);
    void excluir(EventoId id);
    boolean existe(EventoId id);
} 