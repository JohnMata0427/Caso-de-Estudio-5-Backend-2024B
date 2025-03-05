package com.backend.conferencias.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 9)
  private String codigo;

  @Column(nullable = false)
  private String descripcion;

  @Column(nullable = false)
  private Long id_conferencista;

  @Column(nullable = false)
  private Long id_auditorio;

  @ManyToOne
  @JoinColumn(name = "id_conferencista", insertable = false, updatable = false)
  @JsonIgnore
  private Conferencista conferencista;

  @ManyToOne
  @JoinColumn(name = "id_auditorio", insertable = false, updatable = false)
  @JsonIgnore
  private Auditorio auditorio;
}
