package com.backend.conferencias.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AuditorioDTO", description = "Modelo de datos para el registro de auditorios que incluye información básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class AuditorioDTO {
  @Hidden
  @Null(message = "El id de la auditorio no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Código único de la auditorio", example = "ED6P2A10", pattern = "^ED\\d{1,2}P\\d{1,2}A\\d{1,3}$")
  @NotBlank(message = "El código de la auditorio es obligatorio")
  @Pattern(regexp = "^ED\\d{1,2}P\\d{1,2}A\\d{1,3}$", message = "El código de la auditorio debe seguir el formato 'ED#P#A#' donde # son números")
  private String codigo;

  @Schema(description = "Nombre de la auditorio", example = "Psicología", minLength = 3, maxLength = 50)
  @NotBlank(message = "El nombre de la auditorio es obligatorio")
  @Length(min = 3, max = 50, message = "El nombre de la auditorio debe tener entre 3 y 50 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre de la auditorio solo puede contener letras, espacios y caracteres especiales")
  private String nombre;

  @Schema(description = "Ubicación de la auditorio", example = "Edificio 6, Piso 3, Aula 10", minLength = 26, maxLength = 29)
  @NotBlank(message = "La ubicación de la auditorio es obligatoria")
  @Pattern(regexp = "^Edificio \\d{1,2}, Piso \\d{1,2}, Aula \\d{1,3}$", message = "La ubicación de la auditorio debe tener el formato 'Edificio X, Piso Y, Aula Z' donde X, Y y Z son números de 1 o 2 dígitos")
  private String ubicacion;

  @Schema(description = "Capacidad de la auditorio", example = "20", minimum = "15", maximum = "35")
  @NotNull(message = "La capacidad es obligatoria")
  @Min(value = 15, message = "La capacidad no puede ser menor a 15 personas")
  @Max(value = 35, message = "La capacidad no puede ser mayor a 35 personas")
  @Digits(integer = 2, fraction = 0, message = "La capacidad debe ser un número entero de máximo 2 dígitos (valor máximo: 35)")
  private Integer capacidad;

  @Schema(description = "Descripción de la auditorio", example = "Aula de clases para la carrera de Psicología", minLength = 10, maxLength = 200, pattern = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){5}).{10,200}$")
  @NotBlank(message = "La descripción de la auditorio es obligatoria")
  @Length(min = 10, max = 200, message = "La descripción de la auditorio debe tener entre 10 y 200 caracteres")
  @Pattern(regexp = "^(?=(.*[A-Za-zñÑáéíóúÁÉÍÓÚ]){5}).{10,200}$", message = "La descripción debe tener al menos 5 letras")
  private String descripcion;

  @Hidden
  public Boolean matchCodigoAndUbicacion() {
    String[] codigoParts = this.codigo.split("(?<=\\D)(?=\\d)");
    String[] ubicacionParts = this.ubicacion.split("(?<=\\D)(?=\\d)");

    return codigoParts[0].equals(ubicacionParts[0]) && codigoParts[1].equals(ubicacionParts[1])
        && codigoParts[2].equals(ubicacionParts[2]);
  }
}
