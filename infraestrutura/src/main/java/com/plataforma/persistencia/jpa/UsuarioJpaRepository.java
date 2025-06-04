package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Integer> {
    List<UsuarioJpa> findByOrderByNome();
    Optional<UsuarioJpa> findByEmail(String email);
    Optional<UsuarioJpa> findByEmailAndSenha(String email, String senha);
} 