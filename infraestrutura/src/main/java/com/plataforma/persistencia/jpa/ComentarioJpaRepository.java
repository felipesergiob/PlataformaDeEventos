package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioJpaRepository extends JpaRepository<ComentarioJpa, Integer> {
    List<ComentarioJpa> findByEventoIdOrderByDataCriacaoDesc(Integer eventoId);
    List<ComentarioJpa> findByUsuarioIdOrderByDataCriacaoDesc(Integer usuarioId);
    List<ComentarioJpa> findByComentarioPaiIdOrderByDataCriacaoAsc(Integer comentarioPaiId);
    int countByEventoId(Integer eventoId);
} 