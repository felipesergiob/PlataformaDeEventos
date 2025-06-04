package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteJpaRepository extends JpaRepository<ParticipanteJpa, Integer> {
    Optional<ParticipanteJpa> findByEventoIdAndUsuarioId(Integer eventoId, Integer usuarioId);
    List<ParticipanteJpa> findByEventoIdOrderByDataCriacaoDesc(Integer eventoId);
    List<ParticipanteJpa> findByUsuarioIdOrderByDataCriacaoDesc(Integer usuarioId);
    int countByEventoIdAndStatus(Integer eventoId, String status);
} 