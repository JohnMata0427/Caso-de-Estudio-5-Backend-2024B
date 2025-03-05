package com.backend.conferencias.controllers;

import com.backend.conferencias.dto.LoginUsuarioDTO;
import com.backend.conferencias.dto.RegistroUsuarioDTO;
import com.backend.conferencias.services.AuthService;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para la autenticación de usuarios en el sistema")
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
		@Content(mediaType = "")
})
public class AuthController {

	@Autowired
	private AuthService usuarioService;

	@Operation(summary = "Registro de usuario", description = "Registro de un nuevo usuario en el sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = {
					@Content(mediaType = "application/json")
			}),
			@ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe un usuario con el mismo correo", content = {
					@Content(mediaType = "application/json")
			})
	})
	@PostMapping("/register")
	public ResponseEntity<Object> register(
			@Valid @RequestBody RegistroUsuarioDTO usuario) {
		try {
			return ResponseEntity.status(201).body(usuarioService.registro(usuario));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
		}
	}

	@Operation(summary = "Inicio de sesión", description = "Inicio de sesión de un usuario en el sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = {
					@Content(mediaType = "application/json")
			}),
			@ApiResponse(responseCode = "400", description = "Correo o contraseña incorrectos", content = {
					@Content(mediaType = "application/json")
			})
	})
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginUsuarioDTO request) {
		try {
			return ResponseEntity.ok(usuarioService.iniciarSesion(request));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("response",
					e instanceof BadCredentialsException ? "Correo o contraseña incorrectos" : e.getMessage()));
		}
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Perfil de usuario", description = "Obtener información del usuario autenticado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Perfil de usuario obtenido exitosamente", content = {
					@Content(mediaType = "application/json")
			}),
			@ApiResponse(responseCode = "400", description = "El usuario no se encuentra autenticado", content = {
					@Content(mediaType = "application/json")
			})
	})
	@GetMapping("/profile")
	public ResponseEntity<Object> profile() {
		try {
			return ResponseEntity.ok(usuarioService.perfil());
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
		}
	}
}