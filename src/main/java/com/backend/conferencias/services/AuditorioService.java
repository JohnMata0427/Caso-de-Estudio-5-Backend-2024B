package com.backend.conferencias.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.backend.conferencias.dto.AuditorioDTO;
import com.backend.conferencias.models.Auditorio;
import com.backend.conferencias.models.Reserva;
import com.backend.conferencias.repositories.AuditorioRepository;
import com.backend.conferencias.repositories.ReservaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditorioService {
  @Autowired
  private AuditorioRepository auditorioRepository;

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Auditorio> obtenerTodasLasAuditorios() {
    return auditorioRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerAuditorioPorId(Long id) {
    Auditorio auditorio = auditorioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Auditorio con id " + id + " no encontrada"));

    List<Reserva> reservas = reservaRepository.findByAuditorioId(id);

    return Optional.of(Map.of("auditorio", auditorio, "reservas", reservas));
  }

  @Transactional
  public Auditorio crearAuditorio(AuditorioDTO auditorio) {
    String codigo = auditorio.getCodigo();

    if (auditorio.matchCodigoAndUbicacion())
      throw new RuntimeException("El código y la ubicación deben tener el mismo número de edificio, piso y aula (Ejemplo: ED6P2A10 y Edificio 6, Piso 2, Aula 10)");

    if (auditorioRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una auditorio con el código " + codigo);

    Auditorio auditorioToSave = modelMapper.map(auditorio, Auditorio.class);

    return auditorioRepository.save(auditorioToSave);
  }

  @Transactional
  public Auditorio actualizarAuditorio(Long id, AuditorioDTO auditorioActualizada) {
    Auditorio auditorio = auditorioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Auditorio con id " + id + " no encontrada"));

    String codigo = auditorioActualizada.getCodigo();

    if (!codigo.equals(auditorio.getCodigo()) && auditorioRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una auditorio con el código " + codigo);

    Auditorio auditorioToUpdate = modelMapper.map(auditorioActualizada, Auditorio.class);

    auditorioToUpdate.setId(id);

    return auditorioRepository.save(auditorioToUpdate);
  }

  @Transactional
  public void eliminarAuditorio(Long id) {
    auditorioRepository.deleteById(id);
  }
}