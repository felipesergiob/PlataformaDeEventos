package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Comentario {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private String texto;
    private LocalDateTime dataComentario;
    private List<Comentario> respostas = new ArrayList<>();

    public void adicionarResposta(Comentario resposta) {
        respostas.add(resposta);
    }
} 