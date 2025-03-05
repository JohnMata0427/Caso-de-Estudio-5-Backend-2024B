package com.backend.conferencias.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditorios")
public class Auditorio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 7)
  private String codigo;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String ubicacion;

  @Column(nullable = false)
  private Integer capacidad;

  @Column(nullable = false)
  private String descripcion;

  @OneToMany(mappedBy = "auditorio", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonIgnore
  private List<Reserva> reservas;
}