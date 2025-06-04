package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoJpaRepository extends JpaRepository<AvaliacaoJpa, Integer> {
    List<AvaliacaoJpa> findByEventoIdOrderByDataCriacaoDesc(Integer eventoId);
    List<AvaliacaoJpa> findByUsuarioIdOrderByDataCriacaoDesc(Integer usuarioId);
    Optional<AvaliacaoJpa> findByEventoIdAndUsuarioId(Integer eventoId, Integer usuarioId);
    List<AvaliacaoJpa> findByEventoId(Integer eventoId);
} 