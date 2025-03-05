package com.backend.conferencias.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CredencialesDTO", description = "Credenciales de acceso de un usuario registrado en el sistema", requiredMode = Schema.RequiredMode.REQUIRED)
public class LoginUsuarioDTO {
  @Schema(description = "Correo electrónico del usuario", example = "juanperez1234@example.com", required = true, format = "email")
  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El formato del correo electrónico es inválido")
  private String email;

  @Schema(description = "Contraseña del usuario", example = "Password1234.", required = true, format = "password")
  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}
