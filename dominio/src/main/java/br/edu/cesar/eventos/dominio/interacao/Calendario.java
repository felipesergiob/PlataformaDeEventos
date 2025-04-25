package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Calendario {
    private Map<EventoId, TipoInteracao> eventos;
    private TipoVisualizacao visualizacaoAtual;
    private UsuarioId usuarioId;
    
    // Mapa temporário para compatibilidade com testes existentes
    private Map<Evento, EventoId> eventoParaId;

    public enum TipoInteracao {
        SALVO("Azul"),
        CONFIRMADO("Verde"),
        TALVEZ("Amarelo");

        private final String cor;

        TipoInteracao(String cor) {
            this.cor = cor;
        }

        public String getCor() {
            return cor;
        }
    }

    public enum TipoVisualizacao {
        MENSAL,
        SEMANAL,
        DIARIA
    }

    public Calendario(Usuario usuario) {
        this.eventos = new HashMap<>();
        this.eventoParaId = new HashMap<>();
        this.visualizacaoAtual = TipoVisualizacao.MENSAL;
        this.usuarioId = usuario.getId();
    }
    
    // Método para compatibilidade com testes existentes
    public void adicionarEvento(Evento evento, TipoInteracao tipo) {
        EventoId eventoId = evento.getId();
        if (eventoId == null) {
            eventoId = new EventoId();
            evento.setId(eventoId);
        }
        eventos.put(eventoId, tipo);
        eventoParaId.put(evento, eventoId);
    }
    
    // Novo método conforme CML
    public void adicionarEvento(EventoId eventoId, TipoInteracao tipo) {
        eventos.put(eventoId, tipo);
    }
    
    // Método para compatibilidade com testes existentes
    public List<Evento> getEventosPorTipo(TipoInteracao tipo) {
        return eventoParaId.entrySet().stream()
            .filter(entry -> eventos.get(entry.getValue()) == tipo)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    // Novo método conforme CML
    public List<EventoId> getEventosIdPorTipo(TipoInteracao tipo) {
        return eventos.entrySet().stream()
            .filter(entry -> entry.getValue() == tipo)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    // Método para compatibilidade com testes existentes
    public String getCorDoEvento(Evento evento) {
        EventoId eventoId = eventoParaId.get(evento);
        return eventos.get(eventoId).getCor();
    }
    
    // Novo método conforme CML
    public String getCorDoEvento(EventoId eventoId) {
        return eventos.get(eventoId).getCor();
    }

    public void setVisualizacao(TipoVisualizacao visualizacao) {
        this.visualizacaoAtual = visualizacao;
    }

    public TipoVisualizacao getVisualizacaoAtual() {
        return visualizacaoAtual;
    }
    
    // Método para compatibilidade com testes existentes
    public List<Evento> getEventos() {
        return new ArrayList<>(eventoParaId.keySet());
    }
    
    // Novo método conforme CML
    public List<EventoId> getEventosIds() {
        return new ArrayList<>(eventos.keySet());
    }
    
    public UsuarioId getUsuarioId() {
        return usuarioId;
    }
}