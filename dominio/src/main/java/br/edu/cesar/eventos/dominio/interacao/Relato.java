package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Relato {
    private EventoId eventoId;
    private UsuarioId usuarioId;
    private String texto;
    private List<String> fotos = new ArrayList<>();
    private LocalDateTime dataRelato;

    public void adicionarFoto(String foto) {
        fotos.add(foto);
    }
}