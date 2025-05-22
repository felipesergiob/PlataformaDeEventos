package com.plataforma.inscricao;

import com.plataforma.compartilhado.UsuarioId;
import com.plataforma.compartilhado.EventoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, InscricaoId> {
    List<Inscricao> findByUsuarioId(UsuarioId usuarioId);
    List<Inscricao> findByEventoId(EventoId eventoId);
    Optional<Inscricao> findByUsuarioIdAndEventoId(UsuarioId usuarioId, EventoId eventoId);
    boolean existsByUsuarioIdAndEventoId(UsuarioId usuarioId, EventoId eventoId);
} 