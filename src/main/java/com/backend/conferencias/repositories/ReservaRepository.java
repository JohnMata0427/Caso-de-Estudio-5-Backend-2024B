package com.backend.conferencias.repositories;

import com.backend.conferencias.models.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
  List<Reserva> findByConferencistaId(Long conferencistaId);

  List<Reserva> findByAuditorioId(Long auditorioId);

  boolean existsByCodigo(String codigo);

  boolean existsByConferencistaIdAndAuditorioId(Long conferencistaId, Long auditorioId);
}
