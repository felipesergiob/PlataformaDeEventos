package com.plataforma.persistencia.jpa.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface UsuarioJpaRepositorio extends JpaRepository<UsuarioJpa, Long> {
    UsuarioJpa findByEmail(String email);
} 