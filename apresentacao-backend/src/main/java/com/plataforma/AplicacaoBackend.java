package com.plataforma.plataforma.apresentacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.plataforma.usuario.UsuarioService;
import com.plataforma.usuario.UsuarioRepositorio;

@SpringBootApplication
public class AplicacaoBackend {

    @Bean
    public UsuarioService usuarioService(UsuarioRepositorio repositorio) {
        return new UsuarioService(repositorio);
    }

    @Bean
    public UsuarioServiceAplicacao usuarioServiceAplicacao(UsuarioRepositorioAplicacao repositorio) {
        return new UsuarioServiceAplicacao(repositorio);
    }

    public static void main(String[] args) {
        SpringApplication.run(AplicacaoBackend.class, args);
    }
}
