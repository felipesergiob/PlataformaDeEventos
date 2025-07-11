package com.plataforma.compartilhado;

import lombok.Getter;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.Validate;

@Getter
public class AvaliacaoId {
    private static final Random random = new Random();
    private final int id;

    private AvaliacaoId(int id) {
        Validate.notNull(id, "O código não pode ser nulo ou vazio");
        Validate.isTrue(id > 0, "O código não pode ser menor que 1");

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AvaliacaoId random() {
        return new AvaliacaoId(random.nextInt(Integer.MAX_VALUE - 1) + 1);
    }

    public static AvaliacaoId de(int id) {
        return new AvaliacaoId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof AvaliacaoId) {
            AvaliacaoId avaliacaoId = (AvaliacaoId) obj;

            return id == avaliacaoId.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}