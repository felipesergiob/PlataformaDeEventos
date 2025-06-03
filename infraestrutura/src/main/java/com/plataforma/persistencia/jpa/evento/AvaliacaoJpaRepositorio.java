package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoJpaRepositorio extends JpaRepository<AvaliacaoJpa, Long> {
    List<AvaliacaoJpa> findByEventoId(Long eventoId);
    
    @Query("SELECT AVG(a.nota) FROM AvaliacaoJpa a WHERE a.evento.id = ?1")
    Double findMediaNotaByEventoId(Long eventoId);
    
    boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);
} 