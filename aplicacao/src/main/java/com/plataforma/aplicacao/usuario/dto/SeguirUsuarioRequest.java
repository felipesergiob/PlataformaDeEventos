package com.plataforma.aplicacao.usuario.dto;

public record SeguirUsuarioRequest(
    Integer seguidorId,
    Integer seguidoId
) {}