package com.plataforma.apresentacao.config;

import com.plataforma.aplicacao.usuario.UsuarioResumo;
import com.plataforma.aplicacao.usuario.UsuarioResumoImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Configura o mapeamento para sempre usar UsuarioResumoImpl quando o destino é UsuarioResumo
        modelMapper.createTypeMap(Object.class, UsuarioResumo.class)
            .setConverter(context -> modelMapper.map(context.getSource(), UsuarioResumoImpl.class));

        return modelMapper;
    }
} 