package com.plataforma.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Integer> {
    UsuarioJpa findByEmail(String email);
} 