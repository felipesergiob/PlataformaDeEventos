package com.plataforma.plataforma.persistencia.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JpaMapeador {
    private final ModelMapper modelMapper;

    public JpaMapeador() {
        this.modelMapper = new ModelMapper();
    }

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}