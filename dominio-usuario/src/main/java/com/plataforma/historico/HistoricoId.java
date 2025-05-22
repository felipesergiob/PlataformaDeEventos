package com.plataforma.historico;

import java.util.UUID;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class HistoricoId {
    private final String codigo;

    public HistoricoId(String codigo) {
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

    public static HistoricoId novo() {
        return new HistoricoId(UUID.randomUUID().toString());
    }

    public static HistoricoId de(String valor) {
        notNull(valor, "O valor não pode ser nulo");
        notBlank(valor, "O valor não pode estar em branco");
        return new HistoricoId(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HistoricoId historicoId = (HistoricoId) obj;
        return codigo.equals(historicoId.codigo);
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