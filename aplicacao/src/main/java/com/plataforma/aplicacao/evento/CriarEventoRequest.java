package com.plataforma.aplicacao.evento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarEventoRequest {
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDateTime dataFim;

    @NotBlank(message = "O local é obrigatório")
    @Size(max = 200, message = "O local deve ter no máximo 200 caracteres")
    private String local;

    @Size(max = 100, message = "O gênero deve ter no máximo 100 caracteres")
    private String genero;

    @NotNull(message = "O valor é obrigatório")
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private BigDecimal valor;

    private String imagem;

    @NotNull(message = "O ID do organizador é obrigatório")
    private Integer organizadorId;
} 