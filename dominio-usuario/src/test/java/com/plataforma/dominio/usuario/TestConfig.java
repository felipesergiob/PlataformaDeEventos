package com.plataforma.dominio.usuario;

import com.plataforma.evento.EventoRepository;
import com.plataforma.usuario.UsuarioService;
import com.plataforma.usuario.UsuarioRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public EventoRepository eventoRepository() {
        return Mockito.mock(EventoRepository.class);
    }

    @Bean
    @Primary
    public UsuarioRepository usuarioRepository() {
        return Mockito.mock(UsuarioRepository.class);
    }

    @Bean
    @Primary
    public UsuarioService usuarioService(UsuarioRepository usuarioRepository, 
                                       EventoRepository eventoRepository) {
        return new UsuarioService(usuarioRepository, eventoRepository);
    }
} 