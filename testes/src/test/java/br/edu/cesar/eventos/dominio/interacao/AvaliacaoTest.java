package br.edu.cesar.eventos.dominio.interacao;

import br.edu.cesar.eventos.dominio.evento.EventoId;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class AvaliacaoTest {

    @Test
    public void testValidarNota() {
        // Arrange
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setEventoId(new EventoId(UUID.randomUUID()));
        avaliacao.setUsuarioId(new UsuarioId(UUID.randomUUID()));
        
        // Act & Assert - Notas válidas
        avaliacao.setNota(1);
        assertTrue(avaliacao.validarNota());
        
        avaliacao.setNota(3);
        assertTrue(avaliacao.validarNota());
        
        avaliacao.setNota(5);
        assertTrue(avaliacao.validarNota());
        
        // Act & Assert - Notas inválidas
        avaliacao.setNota(0);
        assertFalse(avaliacao.validarNota());
        
        avaliacao.setNota(6);
        assertFalse(avaliacao.validarNota());
        
        avaliacao.setNota(-1);
        assertFalse(avaliacao.validarNota());
    }
} 