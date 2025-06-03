package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ParticipanteEventoJpaRepositorio extends JpaRepository<ParticipanteEventoJpa, Long> {
    boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);
    
    List<ParticipanteEventoJpa> findByUsuarioId(Long usuarioId);
    
    @Query("SELECT pe FROM ParticipanteEventoJpa pe WHERE pe.usuario.id = ?1 AND pe.evento.dataFim > ?2")
    List<ParticipanteEventoJpa> findEventosFuturosByUsuario(Long usuarioId, LocalDateTime agora);
} 