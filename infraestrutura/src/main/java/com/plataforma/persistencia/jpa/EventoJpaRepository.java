package com.plataforma.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
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

    @Query(value = """
        SELECT * FROM evento e
        WHERE (:genero IS NULL OR e.genero = :genero)
        AND (:dataInicio IS NULL OR e.data_inicio >= :dataInicio)
        AND (:dataFim IS NULL OR e.data_fim <= :dataFim)
        AND (:precoMinimo IS NULL OR e.valor >= :precoMinimo)
        AND (:precoMaximo IS NULL OR e.valor <= :precoMaximo)
        AND (:gratuito IS NULL OR (:gratuito = true AND e.valor = 0))
        AND (
            :periodoHorario IS NULL OR :periodoHorario = '' OR
            (
                (:periodoHorario = 'MANHA' AND EXTRACT(HOUR FROM e.data_inicio) BETWEEN 6 AND 11) OR
                (:periodoHorario = 'TARDE' AND EXTRACT(HOUR FROM e.data_inicio) BETWEEN 12 AND 17) OR
                (:periodoHorario = 'NOITE' AND (EXTRACT(HOUR FROM e.data_inicio) >= 18 OR EXTRACT(HOUR FROM e.data_inicio) < 6))
            )
        )
        ORDER BY e.data_inicio ASC
    """, nativeQuery = true)
    List<EventoJpa> buscarEventosComFiltro(
        @Param("genero") String genero,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim,
        @Param("precoMinimo") Double precoMinimo,
        @Param("precoMaximo") Double precoMaximo,
        @Param("periodoHorario") String periodoHorario,
        @Param("gratuito") Boolean gratuito
    );
}