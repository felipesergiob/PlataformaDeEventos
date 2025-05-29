package com.plataforma.dominio.evento;

import com.plataforma.evento.EventoRepository;
import com.plataforma.evento.EventoService;
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
    public EventoService eventoService(EventoRepository eventoRepository) {
        return new EventoService(eventoRepository);
    }
}