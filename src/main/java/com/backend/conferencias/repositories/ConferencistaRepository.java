package com.backend.conferencias.repositories;

import com.backend.conferencias.models.Conferencista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferencistaRepository extends JpaRepository<Conferencista, Long> {
  boolean existsByCedula(String cedula);

  boolean existsByEmail(String email);

  boolean existsByTelefono(String telefono);
}
