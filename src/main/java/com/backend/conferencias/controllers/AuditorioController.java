package com.backend.conferencias.controllers;

import com.backend.conferencias.dto.AuditorioDTO;
import com.backend.conferencias.models.Auditorio;
import com.backend.conferencias.services.AuditorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auditorios")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Auditorios", description = "Endpoints para gestionar las auditorios del sistema")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class AuditorioController {

  @Autowired
  private AuditorioService auditorioService;

  @Operation(summary = "Obtener todas las auditorios", description = "Obtiene una lista con todas las auditorios registradas en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de auditorios obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Auditorio[].class))
  })
  @GetMapping
  public List<Auditorio> getAllAuditorios() {
    return auditorioService.obtenerTodasLasAuditorios();
  }

  @Operation(summary = "Obtener auditorio por ID", description = "Obtiene una auditorio por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Auditorio obtenida exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de auditorio no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getAuditorioById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(auditorioService.obtenerAuditorioPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear auditorio", description = "Crea una nueva auditorio en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Auditorio creada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Auditorio.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una auditorio con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createAuditorio(@Valid @RequestBody AuditorioDTO auditorio) {
    try {
      return ResponseEntity.ok(auditorioService.crearAuditorio(auditorio));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar auditorio", description = "Actualiza una auditorio existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Auditorioactualizada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Auditorio.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o no existe una auditorio con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateAuditorio(@PathVariable Long id,
      @Valid @RequestBody AuditorioDTO auditorioActualizada) {
    try {
      return ResponseEntity.ok(auditorioService.actualizarAuditorio(id, auditorioActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar auditorio", description = "Elimina una auditorio del sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Auditorio eliminada exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de auditorio no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteAuditorio(@PathVariable Long id) {
    try {
      auditorioService.eliminarAuditorio(id);
      return ResponseEntity.ok(Map.of("response", "Auditorio con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}