package com.plataforma.aplicacao.evento.strategy;

import com.plataforma.aplicacao.evento.EventoResumo;
import java.util.List;

public interface FiltroEventoStrategy {
    List<EventoResumo> filtrar(List<EventoResumo> eventos);
} 