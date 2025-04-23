package br.edu.cesar.eventos.dominio.evento;

import br.edu.cesar.eventos.dominio.usuario.Usuario;
import br.edu.cesar.eventos.dominio.usuario.UsuarioId;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class EventoTest {

    @Test
    public void testAdicionarParticipante() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(new EventoId(UUID.randomUUID()));
        evento.setTitulo("Evento Teste");
        evento.setLimiteParticipantes(10);

        Usuario organizador = new Usuario();
        organizador.setId(new UsuarioId(UUID.randomUUID()));
        organizador.setNome("Organizador");
        evento.setOrganizador(organizador);

        Usuario participante = new Usuario();
        participante.setId(new UsuarioId(UUID.randomUUID()));
        participante.setNome("Participante");

        // Act
        boolean resultado = evento.adicionarParticipante(participante);

        // Assert
        assertTrue(resultado);
        assertTrue(evento.getParticipantes().contains(participante));
    }

    @Test
    public void testAdicionarParticipanteQuandoLotado() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(new EventoId(UUID.randomUUID()));
        evento.setLimiteParticipantes(1);

        Usuario participante1 = new Usuario();
        participante1.setId(new UsuarioId(UUID.randomUUID()));
        evento.adicionarParticipante(participante1);

        Usuario participante2 = new Usuario();
        participante2.setId(new UsuarioId(UUID.randomUUID()));

        // Act
        boolean resultado = evento.adicionarParticipante(participante2);

        // Assert
        assertFalse(resultado);
        assertFalse(evento.getParticipantes().contains(participante2));
    }

    @Test
    public void testMarcarInteresse() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(new EventoId(UUID.randomUUID()));

        Usuario interessado = new Usuario();
        interessado.setId(new UsuarioId(UUID.randomUUID()));

        // Act
        evento.marcarInteresse(interessado);

        // Assert
        assertTrue(evento.getInteressados().contains(interessado));
    }

    @Test
    public void testMarcarTalvez() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(new EventoId(UUID.randomUUID()));

        Usuario usuario = new Usuario();
        usuario.setId(new UsuarioId(UUID.randomUUID()));

        // Act
        evento.marcarTalvez(usuario);

        // Assert
        assertTrue(evento.getTalvezVao().contains(usuario));
    }
} 