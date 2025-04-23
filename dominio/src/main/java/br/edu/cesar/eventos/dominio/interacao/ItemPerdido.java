package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemPerdido {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private String descricao;
    private String localEncontrado;
    private LocalDateTime dataEncontrado;
    private boolean devolvido;

    public void marcarComoDevolvido() {
        this.devolvido = true;
    }
} 