package com.plataforma.plataforma.persistencia.jpa.participanteevento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipanteEventoJpaRepository extends JpaRepository<ParticipanteEventoJpa, UUID> {
    List<ParticipanteEventoJpa> findByEventoId(UUID eventoId);
    List<ParticipanteEventoJpa> findByUsuarioId(UUID usuarioId);
    ParticipanteEventoJpa findByEventoIdAndUsuarioId(UUID eventoId, UUID usuarioId);
} 