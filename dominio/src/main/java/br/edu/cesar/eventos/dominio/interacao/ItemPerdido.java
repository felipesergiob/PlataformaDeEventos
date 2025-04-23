package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import lombok.Data;
import java.time.LocalDateTime;

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

    public void marcarComoDevolvido() {
        this.devolvido = true;
    }

    public boolean isValid() {
        return tipo != null && !tipo.isEmpty() &&
               cor != null && !cor.isEmpty() &&
               descricao != null && !descricao.isEmpty() &&
               localAproximado != null && !localAproximado.isEmpty();
    }
}