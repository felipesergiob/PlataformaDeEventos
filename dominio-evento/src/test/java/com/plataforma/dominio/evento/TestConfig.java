package com.plataforma.dominio.evento;

import com.plataforma.evento.EventoRepository;
import com.plataforma.evento.EventoService;
import com.plataforma.inscricao.InscricaoRepository;
import com.plataforma.inscricao.InscricaoService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public EventoRepository eventoRepository() {
        return Mockito.mock(EventoRepository.class);
    }

    @Bean
    public EventoService eventoService() {
        return Mockito.mock(EventoService.class);
    }

    @Bean
    public InscricaoRepository inscricaoRepository() {
        return Mockito.mock(InscricaoRepository.class);
    }

    @Bean
    public InscricaoService inscricaoService() {
        return Mockito.mock(InscricaoService.class);
    }
} 