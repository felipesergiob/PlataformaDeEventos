package com.plataforma.aplicacao.usuario;

import lombok.Data;

@Data
public class UsuarioResumoImpl implements UsuarioResumo {
    private String id;
    private String nome;
    private String email;
    private String senha;
} 