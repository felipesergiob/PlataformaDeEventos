package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioJpaRepositorio extends JpaRepository<ComentarioJpa, Long> {
    List<ComentarioJpa> findByEventoId(Long eventoId);
    
    List<ComentarioJpa> findByComentarioPaiId(Long comentarioPaiId);
    
    @Query("SELECT c FROM ComentarioJpa c WHERE c.evento.id = ?1 AND c.comentarioPai IS NULL")
    List<ComentarioJpa> findComentariosPrincipaisByEventoId(Long eventoId);
} 