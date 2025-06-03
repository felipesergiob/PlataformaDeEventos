package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicacaoJpaRepositorio extends JpaRepository<PublicacaoJpa, Long> {
    List<PublicacaoJpa> findByEventoId(Long eventoId);
    
    List<PublicacaoJpa> findByUsuarioId(Long usuarioId);
} 