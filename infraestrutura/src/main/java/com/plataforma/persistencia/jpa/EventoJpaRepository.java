package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoJpa, Integer> {
    List<EventoJpa> findByOrganizadorId(Integer organizadorId);

    @Query(value = """
        SELECT 
            ID, TITULO, DESCRICAO, DATA_INICIO, DATA_FIM, LOCAL, GENERO,
            VALOR, IMAGEM, PARTICIPANTES, DATA_CRIACAO, ORGANIZADOR_ID
        FROM EVENTO
        WHERE PARTICIPANTES > 0
        ORDER BY PARTICIPANTES DESC, ID ASC
        LIMIT :limite
        """, nativeQuery = true)
    List<EventoJpa> findEventosDestaqueDaSemana(@Param("limite") int limite);

    List<EventoJpa> findByOrganizadorIdOrderByDataCriacaoDesc(Integer organizadorId);
    Optional<EventoJpa> findByIdAndOrganizadorId(Integer id, Integer organizadorId);
}