package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicacaoJpaRepository extends JpaRepository<PublicacaoJpa, Integer> {
    List<PublicacaoJpa> findByEventoIdOrderByDataCriacaoDesc(Integer eventoId);
    List<PublicacaoJpa> findByUsuarioIdOrderByDataCriacaoDesc(Integer usuarioId);
} 