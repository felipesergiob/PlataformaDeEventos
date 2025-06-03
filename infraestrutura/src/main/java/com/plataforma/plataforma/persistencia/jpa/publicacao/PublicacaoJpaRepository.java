package com.plataforma.plataforma.persistencia.jpa.publicacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PublicacaoJpaRepository extends JpaRepository<PublicacaoJpa, UUID> {
    List<PublicacaoJpa> findByEventoId(UUID eventoId);
    List<PublicacaoJpa> findByUsuarioId(UUID usuarioId);
} 