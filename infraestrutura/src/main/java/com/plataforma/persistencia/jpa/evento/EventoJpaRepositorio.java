package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface EventoJpaRepositorio extends JpaRepository<EventoJpa, Long> {
    List<EventoJpa> findByGenero(String genero);
    
    List<EventoJpa> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<EventoJpa> findByValorLessThanEqual(BigDecimal valor);
    
    @Query("SELECT e FROM EventoJpa e WHERE e.organizador.id = ?1 AND e.dataFim < ?2")
    List<EventoJpa> findEventosPassadosByOrganizador(Long organizadorId, LocalDateTime agora);
    
    @Query("SELECT e FROM EventoJpa e WHERE e.participantes > 0 ORDER BY e.participantes DESC")
    List<EventoJpa> findEventosDestaque();
} 