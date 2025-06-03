package com.plataforma.persistencia.jpa.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface EventoJpaRepositorio extends JpaRepository<EventoJpa, Long> {
    List<EventoJpa> findByGenero(String genero);

    List<EventoJpa> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<EventoJpa> findByValorLessThanEqual(BigDecimal valor);

    List<EventoJpa> findByDataInicioBetweenAndParticipantesGreaterThanOrderByParticipantesDesc(
        LocalDateTime inicioSemana, LocalDateTime fimSemana, Integer minParticipantes);

    List<EventoJpa> findByGeneroOrderByDataInicioAsc(String genero);

    List<EventoJpa> findByParticipantesGreaterThanOrderByParticipantesDesc(Integer minParticipantes);

    List<EventoJpa> findByDataInicioAfterOrderByDataInicioAsc(LocalDateTime data);

    @Query("SELECT e FROM EventoJpa e WHERE e.organizador.id = ?1 AND e.dataFim < ?2")
    List<EventoJpa> findEventosPassadosByOrganizador(Long organizadorId, LocalDateTime agora);

    @Query("SELECT e FROM EventoJpa e WHERE e.genero = :genero AND HOUR(e.dataInicio) >= :horaInicio AND HOUR(e.dataInicio) < :horaFim")
    List<EventoJpa> findByGeneroAndPeriodoDia(@Param("genero") String genero, 
                                            @Param("horaInicio") int horaInicio,
                                            @Param("horaFim") int horaFim);

    @Query("SELECT e FROM EventoJpa e WHERE HOUR(e.dataInicio) >= :horaInicio AND HOUR(e.dataInicio) < :horaFim")
    List<EventoJpa> findByPeriodoDia(@Param("horaInicio") int horaInicio,
                                    @Param("horaFim") int horaFim);

    @Query("SELECT e FROM EventoJpa e WHERE " +
           "(:genero IS NULL OR e.genero = :genero) AND " +
           "(:dataInicio IS NULL OR e.dataInicio >= :dataInicio) AND " +
           "(:dataFim IS NULL OR e.dataFim <= :dataFim) AND " +
           "(:valorMaximo IS NULL OR e.valor <= :valorMaximo) AND " +
           "(:horaInicio IS NULL OR (HOUR(e.dataInicio) >= :horaInicio AND HOUR(e.dataInicio) < :horaFim))")
    List<EventoJpa> filtrarEventos(@Param("genero") String genero,
                                  @Param("dataInicio") LocalDateTime dataInicio,
                                  @Param("dataFim") LocalDateTime dataFim,
                                  @Param("valorMaximo") BigDecimal valorMaximo,
                                  @Param("horaInicio") Integer horaInicio,
                                  @Param("horaFim") Integer horaFim);
}