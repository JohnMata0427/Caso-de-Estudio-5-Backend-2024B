package com.backend.conferencias.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.conferencias.repositories.ReservaRepository;

import com.backend.conferencias.dto.ReservaDTO;
import com.backend.conferencias.models.Conferencista;
import com.backend.conferencias.models.Auditorio;
import com.backend.conferencias.models.Reserva;
import com.backend.conferencias.repositories.ConferencistaRepository;
import com.backend.conferencias.repositories.AuditorioRepository;

@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private ConferencistaRepository conferencistaRepository;

  @Autowired
  private AuditorioRepository auditorioRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Reserva> obtenerTodasLasReservas() {
    return reservaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerReservaPorId(Long id) {
    Reserva reserva = reservaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reserva con id " + id + " no encontrada"));

    Long id_conferencista = reserva.getId_conferencista();
    Conferencista conferencista = conferencistaRepository.findById(id_conferencista)
        .orElseThrow(
            () -> new RuntimeException("Conferencista con id " + id_conferencista + " no encontrado"));

    Long id_auditorio = reserva.getId_auditorio();
    Auditorio auditorio = auditorioRepository.findById(id_auditorio)
        .orElseThrow(() -> new RuntimeException("Auditorio con id " + id_auditorio + " no encontrada"));

    return Optional.of(Map.of("reserva", reserva, "conferencista", conferencista, "auditorio", auditorio));
  }

  @Transactional
  public Reserva crearReserva(ReservaDTO reserva) {
    String codigo = reserva.getCodigo();
    Long id_conferencista = reserva.getId_conferencista();
    Long id_auditorio = reserva.getId_auditorio();

    if (reservaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una reserva con el código " + codigo);


    if (reservaRepository.existsByConferencistaIdAndAuditorioId(id_conferencista, id_auditorio))
      throw new RuntimeException(
          "El conferencista con id " + id_conferencista + " ya está reservado en la auditorio con id "
              + id_auditorio);

    Boolean conferencistaExistente = conferencistaRepository.existsById(id_conferencista);

    if (!conferencistaExistente)
      throw new RuntimeException("Conferencista con id " + id_conferencista + " no encontrado");

    Boolean auditorioExistente = auditorioRepository.existsById(id_auditorio);

    if (!auditorioExistente)
      throw new RuntimeException("Auditorio con id " + id_auditorio + " no encontrada");

    Reserva reservaToSave = modelMapper.map(reserva, Reserva.class);

    return reservaRepository.save(reservaToSave);
  }

  @Transactional
  public Reserva actualizarReserva(Long id, ReservaDTO reservaActualizada) {
    Reserva reserva = reservaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

    String codigo = reservaActualizada.getCodigo();
    Long id_conferencista = reservaActualizada.getId_conferencista();
    Long id_auditorio = reservaActualizada.getId_auditorio();

    if (!codigo.equals(reserva.getCodigo()) && reservaRepository.existsByCodigo(codigo))
      throw new RuntimeException("Ya existe una reserva con el código " + codigo);

    Boolean actualizarConferencista = !id_conferencista.equals(reserva.getId_conferencista());
    Boolean actualizarAuditorio = !id_auditorio.equals(reserva.getId_auditorio());

    if ((actualizarConferencista || actualizarAuditorio)
        && reservaRepository.existsByConferencistaIdAndAuditorioId(id_conferencista, id_auditorio))
      throw new RuntimeException(
          "El conferencista con id " + id_conferencista + " ya está reservado en la auditorio con id " + id_auditorio);

    if (actualizarConferencista && !conferencistaRepository.existsById(id_conferencista))
      throw new RuntimeException("Conferencista con id " + id_conferencista + " no encontrado");

    if (actualizarAuditorio && !auditorioRepository.existsById(id_auditorio))
      throw new RuntimeException("Auditorio con id " + id_auditorio + " no encontrada");

    Reserva reservaToUpdate = modelMapper.map(reservaActualizada, Reserva.class);

    reservaToUpdate.setId(id);

    return reservaRepository.save(reservaToUpdate);
  }

  @Transactional
  public void eliminarReserva(Long id) {
    reservaRepository.deleteById(id);
  }
}
