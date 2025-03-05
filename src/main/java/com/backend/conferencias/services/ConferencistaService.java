package com.backend.conferencias.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.backend.conferencias.dto.ConferencistaDTO;
import com.backend.conferencias.models.Conferencista;
import com.backend.conferencias.models.Reserva;
import com.backend.conferencias.repositories.ConferencistaRepository;
import com.backend.conferencias.repositories.ReservaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConferencistaService {

  @Autowired
  private ConferencistaRepository conferencistaRepository;

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<Conferencista> obtenerTodosLosConferencistas() {
    return conferencistaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Object> obtenerConferencistaPorId(Long id) {
    Conferencista conferencista = conferencistaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Conferencista con id " + id + " no encontrado"));
    List<Reserva> reservas = reservaRepository.findByConferencistaId(id);

    return Optional.of(Map.of("conferencista", conferencista, "reservas", reservas));
  }

  @Transactional
  public Conferencista crearConferencista(ConferencistaDTO conferencista) {
    String cedula = conferencista.getCedula();
    String email = conferencista.getEmail();
    String telefono = conferencista.getTelefono();

    if (!conferencista.isBetween18And70())
      throw new RuntimeException("El conferencista debe tener entre 18 y 70 años");

    if (conferencistaRepository.existsByCedula(cedula))
      throw new RuntimeException("Ya existe un conferencista con la cédula " + cedula);

    if (conferencistaRepository.existsByEmail(email))
      throw new RuntimeException("Ya existe un conferencista con el email " + email);

    if (conferencistaRepository.existsByTelefono(telefono))
      throw new RuntimeException("Ya existe un conferencista con el teléfono " + telefono);

    Conferencista conferencistaToSave = modelMapper.map(conferencista, Conferencista.class);

    return conferencistaRepository.save(conferencistaToSave);
  }

  @Transactional
  public Conferencista actualizarConferencista(Long id, ConferencistaDTO conferencistaActualizado) {
    Conferencista conferencista = conferencistaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Conferencista no encontrado"));

    String cedula = conferencistaActualizado.getCedula();
    LocalDate fecha_nacimiento = conferencistaActualizado.getFecha_nacimiento();
    String telefono = conferencistaActualizado.getTelefono();
    String email = conferencistaActualizado.getEmail();

    if (!fecha_nacimiento.equals(conferencista.getFecha_nacimiento()) && !conferencistaActualizado.isBetween18And70()) {
      throw new RuntimeException("El conferencista debe tener entre 18 y 70 años");
    }

    if (!cedula.equals(conferencista.getCedula()) && conferencistaRepository.existsByCedula(cedula)) {
      throw new RuntimeException("Ya existe un conferencista con la cédula " + cedula);
    }

    if (!telefono.equals(conferencista.getTelefono()) && conferencistaRepository.existsByTelefono(telefono)) {
      throw new RuntimeException("Ya existe un conferencista con el teléfono " + telefono);
    }

    if (!email.equals(conferencista.getEmail()) && conferencistaRepository.existsByEmail(email)) {
      throw new RuntimeException("Ya existe un conferencista con el email " + email);
    }

    Conferencista conferencistaToUpdate = modelMapper.map(conferencistaActualizado, Conferencista.class);

    conferencistaToUpdate.setId(id);

    return conferencistaRepository.save(conferencistaToUpdate);
  }

  @Transactional
  public void eliminarConferencista(Long id) {
    conferencistaRepository.deleteById(id);
  }
}