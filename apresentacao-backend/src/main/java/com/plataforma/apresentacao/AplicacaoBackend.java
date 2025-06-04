package com.plataforma.apresentacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.plataforma.aplicacao.usuario.UsuarioServicoAplicacao;
import com.plataforma.aplicacao.usuario.UsuarioRepositorioAplicacao;

@SpringBootApplication
@EntityScan(basePackages = {
    "com.plataforma.persistencia.jpa",
    "com.plataforma.infraestrutura.persistencia"
})
@EnableJpaRepositories(basePackages = {
    "com.plataforma.persistencia.jpa",
    "com.plataforma.infraestrutura.persistencia"
})
@ComponentScan(basePackages = {
    "com.plataforma.apresentacao",
    "com.plataforma.persistencia",
    "com.plataforma.aplicacao",
    "com.plataforma.infraestrutura"
})
public class AplicacaoBackend {

    @Bean
    public UsuarioServicoAplicacao usuarioServicoAplicacao(UsuarioRepositorioAplicacao usuarioRepositorioAplicacao) {
        return new UsuarioServicoAplicacao(usuarioRepositorioAplicacao);
    }

    public static void main(String[] args) {
        SpringApplication.run(AplicacaoBackend.class, args);
    }
}