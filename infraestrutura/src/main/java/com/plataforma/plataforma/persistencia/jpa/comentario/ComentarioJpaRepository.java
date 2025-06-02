package com.plataforma.plataforma.persistencia.jpa.comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioJpaRepository extends JpaRepository<ComentarioJpa, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 