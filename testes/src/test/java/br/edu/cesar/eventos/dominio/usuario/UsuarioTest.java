package br.edu.cesar.eventos.dominio.usuario;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class UsuarioTest {

    @Test
    public void testSeguirUsuario() {
        // Arrange
        Usuario usuario1 = new Usuario();
        usuario1.setId(new UsuarioId(UUID.randomUUID()));
        usuario1.setNome("João");

        Usuario usuario2 = new Usuario();
        usuario2.setId(new UsuarioId(UUID.randomUUID()));
        usuario2.setNome("Maria");

        // Act
        usuario1.seguirUsuario(usuario2);

        // Assert
        assertTrue(usuario1.getSeguindo().contains(usuario2));
        assertTrue(usuario2.getSeguidores().contains(usuario1));
    }

    @Test
    public void testPararSeguir() {
        // Arrange
        Usuario usuario1 = new Usuario();
        usuario1.setId(new UsuarioId(UUID.randomUUID()));
        usuario1.setNome("João");

        Usuario usuario2 = new Usuario();
        usuario2.setId(new UsuarioId(UUID.randomUUID()));
        usuario2.setNome("Maria");

        usuario1.seguirUsuario(usuario2);

        // Act
        usuario1.pararSeguir(usuario2);

        // Assert
        assertFalse(usuario1.getSeguindo().contains(usuario2));
        assertFalse(usuario2.getSeguidores().contains(usuario1));
    }

    @Test
    public void testNaoSeguirMesmoUsuarioDuasVezes() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario1.seguirUsuario(usuario2);
        usuario1.seguirUsuario(usuario2);
        
        assertEquals(1, usuario1.getSeguindo().size());
        assertEquals(1, usuario2.getSeguidores().size());
    }
} 