package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class ItemPerdido {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private String tipo;
    private String cor;
    private String descricao;
    private String localAproximado;
    private String foto;
    private LocalDateTime dataReporte;
    private boolean devolvido;
    private LocalDateTime dataDevolucao;
    private String status;
    private int tentativasContato;
    private LocalDateTime ultimaTentativaContato;
    private String observacoesEquipe;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalAproximado() {
        return localAproximado;
    }

    public void setLocalAproximado(String localAproximado) {
        this.localAproximado = localAproximado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDateTime getDataReporte() {
        return dataReporte;
    }

    public void setDataReporte(LocalDateTime dataReporte) {
        this.dataReporte = dataReporte;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTentativasContato() {
        return tentativasContato;
    }

    public void setTentativasContato(int tentativasContato) {
        this.tentativasContato = tentativasContato;
    }

    public LocalDateTime getUltimaTentativaContato() {
        return ultimaTentativaContato;
    }

    public void setUltimaTentativaContato(LocalDateTime ultimaTentativaContato) {
        this.ultimaTentativaContato = ultimaTentativaContato;
    }

    public String getObservacoesEquipe() {
        return observacoesEquipe;
    }

    public void setObservacoesEquipe(String observacoesEquipe) {
        this.observacoesEquipe = observacoesEquipe;
    }

    public void marcarComoDevolvido() {
        if (!devolvido) {
            this.devolvido = true;
            this.dataDevolucao = LocalDateTime.now();
            this.status = "DEVOLVIDO";
        }
    }

    public boolean isValid() {
        return eventoId != null && 
               usuarioId != null && 
               tipo != null && !tipo.isEmpty() &&
               descricao != null && !descricao.isEmpty() &&
               localAproximado != null && !localAproximado.isEmpty() &&
               dataReporte != null;
    }

    public boolean podeSerDevolvido() {
        return !devolvido && 
               status != null && 
               status.equals("ENCONTRADO") &&
               tentativasContato > 0;
    }

    public void registrarTentativaContato(String observacao) {
        this.tentativasContato++;
        this.ultimaTentativaContato = LocalDateTime.now();
        this.observacoesEquipe = observacao;
    }

    public boolean precisaNovaTentativaContato() {
        if (ultimaTentativaContato == null) return true;
        long diasDesdeUltimaTentativa = ChronoUnit.DAYS.between(ultimaTentativaContato, LocalDateTime.now());
        return diasDesdeUltimaTentativa >= 3;
    }

    public void atualizarStatus(String novoStatus) {
        if (!devolvido) {
            this.status = novoStatus;
        }
    }

    public boolean isEmAberto() {
        return !devolvido && status != null && status.equals("EM_ANALISE");
    }
}