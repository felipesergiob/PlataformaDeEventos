package com.plataforma.plataforma.persistencia.jpa.seguidor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguidorJpaRepository extends JpaRepository<SeguidorJpa, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 