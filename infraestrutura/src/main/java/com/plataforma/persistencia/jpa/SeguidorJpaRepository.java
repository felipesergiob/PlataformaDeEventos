package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SeguidorJpaRepository extends JpaRepository<SeguidorJpa, Integer> {
    Optional<SeguidorJpa> findBySeguidorAndSeguido(UsuarioJpa seguidor, UsuarioJpa seguido);
    boolean existsBySeguidorAndSeguido(UsuarioJpa seguidor, UsuarioJpa seguido);
} 