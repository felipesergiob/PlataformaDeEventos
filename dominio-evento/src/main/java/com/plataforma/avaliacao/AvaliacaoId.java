package com.plataforma.avaliacao;

import java.util.UUID;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class AvaliacaoId {
    private final String codigo;

    public AvaliacaoId(String codigo) {
        notNull(codigo, "O código não pode ser nulo");
        notBlank(codigo, "O código não pode estar em branco");
        
        if (!testarCodigo(codigo)) {
            throw new IllegalArgumentException("Código inválido: " + codigo);
        }
        this.codigo = codigo;
    }

    private boolean testarCodigo(String codigo) {
        try {
            UUID.fromString(codigo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public static AvaliacaoId novo() {
        return new AvaliacaoId(UUID.randomUUID().toString());
    }

    public static AvaliacaoId de(String valor) {
        notNull(valor, "O valor não pode ser nulo");
        notBlank(valor, "O valor não pode estar em branco");
        return new AvaliacaoId(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AvaliacaoId avaliacaoId = (AvaliacaoId) obj;
        return codigo.equals(avaliacaoId.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return codigo;
    }
} 