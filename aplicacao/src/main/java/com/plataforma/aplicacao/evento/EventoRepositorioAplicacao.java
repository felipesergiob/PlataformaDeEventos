package com.plataforma.aplicacao.evento;

import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public interface EventoRepositorioAplicacao {
    List<Evento> filtrarEventos(String genero, LocalDateTime data, BigDecimal preco);
    List<Evento> listarEventosCalendario(String usuarioId);
    PerfilOrganizador obterPerfilOrganizador(String organizadorId);
    DashboardEvento obterDashboard(String eventoId);
    void salvarSeguidor(String seguidorId, String seguidoId);
    List<Evento> listarEventosDestaque();
} 