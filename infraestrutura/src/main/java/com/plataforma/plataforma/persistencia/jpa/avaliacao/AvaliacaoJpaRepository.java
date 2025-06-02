package com.plataforma.plataforma.persistencia.jpa.avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoJpaRepository extends JpaRepository<AvaliacaoJpa, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 