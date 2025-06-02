package com.plataforma.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoJpa, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 