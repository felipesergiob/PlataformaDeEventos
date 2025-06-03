package com.plataforma.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoJpa, UUID> {
    List<EventoJpa> findByOrganizadorId(UUID organizadorId);
} 