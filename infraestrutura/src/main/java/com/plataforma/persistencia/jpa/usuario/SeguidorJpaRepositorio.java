package com.plataforma.persistencia.jpa.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface SeguidorJpaRepositorio extends JpaRepository<SeguidorJpa, Long> {
    boolean existsBySeguidorIdAndSeguidoId(Long seguidorId, Long seguidoId);
} 