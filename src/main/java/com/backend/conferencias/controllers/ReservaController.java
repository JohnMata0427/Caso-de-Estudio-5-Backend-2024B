package com.backend.conferencias.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.conferencias.dto.ReservaDTO;
import com.backend.conferencias.models.Reserva;
import com.backend.conferencias.services.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Endpoints para gestionar las reservas del sistema")
@SecurityRequirement(name = "Bearer Authentication")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class ReservaController {

  @Autowired
  private ReservaService reservaService;

  @Operation(summary = "Obtener todas las reservas", description = "Obtiene una lista con todas las reservas registradas en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva[].class))
  })
  @GetMapping
  public List<Reserva> getAllReservas() {
    return reservaService.obtenerTodasLasReservas();
  }

  @Operation(summary = "Obtener reserva por ID", description = "Obtiene una reserva por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reserva obtenida exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de reserva no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getReservaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear matrícula", description = "Crea una nueva matrícula en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula creada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una matrícula con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createReserva(@Valid @RequestBody ReservaDTO reserva) {
    try {
      return ResponseEntity.ok(reservaService.crearReserva(reserva));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar matrícula", description = "Actualiza una matrícula existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula actualizada exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe una matrícula con el mismo código", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateReserva(@PathVariable Long id,
      @Valid @RequestBody ReservaDTO reservaActualizada) {
    try {
      return ResponseEntity.ok(reservaService.actualizarReserva(id, reservaActualizada));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar matrícula", description = "Elimina una matrícula existente en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Matrícula eliminada exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "No existe una matrícula con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteReserva(@PathVariable Long id) {
    try {
      reservaService.eliminarReserva(id);
      return ResponseEntity.ok(Map.of("response", "Reserva con id " + id + " eliminada exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

}
