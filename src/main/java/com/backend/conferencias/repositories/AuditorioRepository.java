package com.backend.conferencias.repositories;

import com.backend.conferencias.models.Auditorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditorioRepository extends JpaRepository<Auditorio, Long> {
  boolean existsByCodigo(String codigo);
}