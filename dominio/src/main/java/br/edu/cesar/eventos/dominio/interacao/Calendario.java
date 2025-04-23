package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.Evento;
import br.edu.cesar.eventos.dominio.usuario.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Calendario {
    private Usuario usuario;
    private Map<Evento, TipoInteracao> eventos;
    private TipoVisualizacao visualizacaoAtual;

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
        this.usuario = usuario;
        this.eventos = new HashMap<>();
        this.visualizacaoAtual = TipoVisualizacao.MENSAL;
    }

    public void adicionarEvento(Evento evento, TipoInteracao tipo) {
        eventos.put(evento, tipo);
    }

    public List<Evento> getEventosPorTipo(TipoInteracao tipo) {
        return eventos.entrySet().stream()
            .filter(entry -> entry.getValue() == tipo)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public String getCorDoEvento(Evento evento) {
        return eventos.get(evento).getCor();
    }

    public void setVisualizacao(TipoVisualizacao visualizacao) {
        this.visualizacaoAtual = visualizacao;
    }

    public TipoVisualizacao getVisualizacaoAtual() {
        return visualizacaoAtual;
    }

    public List<Evento> getEventos() {
        return new ArrayList<>(eventos.keySet());
    }
} 