package com.plataforma.evento;

import com.plataforma.compartilhado.EventoId;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface EventoRepository {
    void salvar(Evento evento);

    Evento obter(EventoId id);

    List<Evento> listarTodos();

    List<Evento> listarPorFiltros(String genero, LocalTime horarioInicio, LocalTime horarioFim, LocalDate data, BigDecimal preco);
} 