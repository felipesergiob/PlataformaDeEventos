package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoJpa, Integer> {
    List<EventoJpa> findByOrganizadorId(Integer organizadorId);
} 