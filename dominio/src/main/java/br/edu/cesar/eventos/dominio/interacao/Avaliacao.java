package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private int nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private boolean moderada;
    private String statusModeracao;
    private LocalDateTime dataModeracao;
    private String motivoRejeicao;
    private int likes;
    private List<UsuarioId> usuariosQueCurtiram;

    public Avaliacao() {
        this.dataAvaliacao = LocalDateTime.now();
        this.moderada = false;
        this.statusModeracao = "PENDENTE";
        this.likes = 0;
        this.usuariosQueCurtiram = new ArrayList<>();
    }

    public boolean isValid() {
        return eventoId != null && 
               usuarioId != null && 
               nota >= 1 && nota <= 5 &&
               comentario != null && !comentario.isEmpty() &&
               dataAvaliacao != null;
    }

    public boolean validarNota() {
        return nota >= 1 && nota <= 5;
    }

    public boolean podeSerModerada() {
        return !moderada && 
               dataAvaliacao != null && 
               comentario != null && !comentario.isEmpty();
    }

    public void moderar(String status, String motivo) {
        if (podeSerModerada()) {
            this.moderada = true;
            this.statusModeracao = status;
            this.dataModeracao = LocalDateTime.now();
            this.motivoRejeicao = motivo;
        }
    }

    public boolean podeSerEditada() {
        if (moderada && statusModeracao.equals("REJEITADA")) {
            return false;
        }
        long horasDesdeAvaliacao = ChronoUnit.HOURS.between(dataAvaliacao, LocalDateTime.now());
        return horasDesdeAvaliacao <= 24;
    }

    public void curtir(UsuarioId usuarioId) {
        if (!usuariosQueCurtiram.contains(usuarioId)) {
            usuariosQueCurtiram.add(usuarioId);
            likes++;
        }
    }

    public void removerCurtida(UsuarioId usuarioId) {
        if (usuariosQueCurtiram.contains(usuarioId)) {
            usuariosQueCurtiram.remove(usuarioId);
            likes--;
        }
    }

    public boolean isAprovada() {
        return moderada && statusModeracao.equals("APROVADA");
    }

    public boolean isRejeitada() {
        return moderada && statusModeracao.equals("REJEITADA");
    }

    public void validar() {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        if (eventoId == null) {
            throw new IllegalArgumentException("O evento é obrigatório");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("O usuário é obrigatório");
        }
    }

    public EventoId getEventoId() {
        return eventoId;
    }

    public void setEventoId(EventoId eventoId) {
        this.eventoId = eventoId;
    }

    public UsuarioId getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioId usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public boolean isModerada() {
        return moderada;
    }

    public String getStatusModeracao() {
        return statusModeracao;
    }

    public LocalDateTime getDataModeracao() {
        return dataModeracao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public int getLikes() {
        return likes;
    }

    public List<UsuarioId> getUsuariosQueCurtiram() {
        return usuariosQueCurtiram;
    }
}