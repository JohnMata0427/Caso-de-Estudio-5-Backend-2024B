package com.backend.conferencias.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ReservaDTO", description = "Modelo de datos para el registro de auditorios que incluye información básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class ReservaDTO {
  @Hidden
  @Null(message = "El id de la reserva no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Código único de la reserva", example = "RESER0001", pattern = "^RESER\\d{4}$")
  @NotBlank(message = "El código de la reserva es obligatorio")
  @Pattern(regexp = "^RESER\\d{4}$", message = "El código debe tener el formato RESER#### donde #### son números")
  private String codigo;

  @Schema(description = "Descripción de la reserva", example = "Segunda reserva del conferencista Juan Pérez en la auditorio de Psicología", minLength = 10, maxLength = 200)
  @NotBlank(message = "La descripción de la reserva es obligatoria")
  @Length(min = 10, max = 200, message = "La descripción de la reserva debe tener entre 10 y 200 caracteres")
  @Pattern(regexp = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){5}).{10,200}$", message = "La descripción debe tener al menos 5 letras")
  private String descripcion;

  @Schema(description = "ID del conferencista", example = "1", minimum = "1", maximum = "999")
  @NotNull(message = "El id del conferencista es obligatorio")
  @Positive(message = "El id del conferencista debe ser un número positivo")
  @Digits(integer = 3, fraction = 0, message = "El id del conferencista debe ser un número entero de máximo 3 dígitos")
  private Long id_conferencista;

  @Schema(description = "ID de la auditorio", example = "1", minimum = "1", maximum = "999")
  @NotNull(message = "El id de la auditorio es obligatorio")
  @Positive(message = "El id de la auditorio debe ser un número positivo")
  @Digits(integer = 3, fraction = 0, message = "El id de la auditorio debe ser un número entero de máximo 3 dígitos")
  private Long id_auditorio;
}
