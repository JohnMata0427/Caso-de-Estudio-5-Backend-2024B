package com.backend.conferencias.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UsuarioDTO", description = "Modelo de datos para el registro de usuarios que incluye información personal básica con validaciones para garantizar la integridad de los datos", requiredMode = Schema.RequiredMode.REQUIRED)
public class RegistroUsuarioDTO {
  @Hidden
  @Null(message = "El id del usuario no debe ser enviado, ya que se genera automáticamente")
  private Long id;

  @Schema(description = "Nombre del usuario", example = "Juan", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 20)
  @NotBlank(message = "El nombre es obligatorio")
  @Length(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
  @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre solo puede contener letras y espacios")
  private String nombre;

  @Schema(description = "Apellido del usuario", example = "Pérez", pattern = "^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$", minLength = 3, maxLength = 20)
  @NotBlank(message = "El apellido es obligatorio")
  @Length(min = 3, max = 20, message = "El apellido de usuario debe tener entre 3 y 20 caracteres")
  private String apellido;

  @Schema(description = "Correo electrónico del usuario", example = "juanperez1234@example.com", format = "email")
  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  @Schema(description = "Contraseña del usuario", example = "Password1234.", format = "password", minLength = 8, pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#.])[A-Za-z\\d$@$!%*?&#.]{8,}$")
  @NotBlank(message = "La contraseña es obligatoria")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#.])[A-Za-z\\d$@$!%*?&#.]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un caracter especial")
  private String password;
}
