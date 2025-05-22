package com.plataforma.item;

import java.util.UUID;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class ItemPerdidoId {
    private final String codigo;

    public ItemPerdidoId(String codigo) {
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

    public static ItemPerdidoId novo() {
        return new ItemPerdidoId(UUID.randomUUID().toString());
    }

    public static ItemPerdidoId de(String valor) {
        notNull(valor, "O valor não pode ser nulo");
        notBlank(valor, "O valor não pode estar em branco");
        return new ItemPerdidoId(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemPerdidoId itemPerdidoId = (ItemPerdidoId) obj;
        return codigo.equals(itemPerdidoId.codigo);
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