package com.plataforma.plataforma.persistencia.jpa.participanteevento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteEventoJpaRepository extends JpaRepository<ParticipanteEventoJpa, Integer> {
    // Métodos customizados podem ser adicionados aqui
} 