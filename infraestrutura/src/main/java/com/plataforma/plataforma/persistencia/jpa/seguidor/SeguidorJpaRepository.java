package com.plataforma.plataforma.persistencia.jpa.seguidor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SeguidorJpaRepository extends JpaRepository<SeguidorJpa, UUID> {
    List<SeguidorJpa> findBySeguidorId(UUID seguidorId);
    List<SeguidorJpa> findBySeguidoId(UUID seguidoId);
    SeguidorJpa findBySeguidorIdAndSeguidoId(UUID seguidorId, UUID seguidoId);
} 