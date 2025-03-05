package com.backend.conferencias.controllers;

import com.backend.conferencias.dto.ConferencistaDTO;
import com.backend.conferencias.models.Conferencista;
import com.backend.conferencias.services.ConferencistaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/conferencistas")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Conferencistas", description = "Endpoints para gestionar los conferencistas del sistema")
@ApiResponse(responseCode = "401", description = "El usuario no tiene permisos para realizar esta operación", content = {
    @Content(mediaType = "")
})
@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
    @Content(mediaType = "")
})
public class ConferencistaController {

  @Autowired
  private ConferencistaService conferencistaService;

  @Operation(summary = "Obtener todos los conferencistas", description = "Obtiene una lista con todos los conferencistas registrados en el sistema")
  @ApiResponse(responseCode = "200", description = "Lista de conferencistas obtenida exitosamente", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Conferencista[].class))
  })
  @GetMapping
  public List<Conferencista> getAllConferencistas() {
    return conferencistaService.obtenerTodosLosConferencistas();
  }

  @Operation(summary = "Obtener conferencista por ID", description = "Obtiene un conferencista por su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Conferencista obtenido exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "ID de conferencista no registrado en el sistema", content = {
          @Content(mediaType = "application/json")
      })
  })
  @GetMapping("/{id}")
  public ResponseEntity<Object> getConferencistaById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(conferencistaService.obtenerConferencistaPorId(id));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Crear conferencista", description = "Crea un nuevo conferencista en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Conferencista creado exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Conferencista.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o ya existe un conferencista con el mismo correo", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PostMapping
  public ResponseEntity<Object> createConferencista(@Valid @RequestBody ConferencistaDTO conferencista) {
    try {
      return ResponseEntity.ok(conferencistaService.crearConferencista(conferencista));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Actualizar conferencista", description = "Actualiza un conferencista en el sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Conferencista actualizado exitosamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Conferencista.class))
      }),
      @ApiResponse(responseCode = "400", description = "Datos inválidos o no existe un conferencista con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateConferencista(@PathVariable Long id,
      @Valid @RequestBody ConferencistaDTO conferencistaActualizado) {
    try {
      return ResponseEntity.ok(conferencistaService.actualizarConferencista(id, conferencistaActualizado));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }

  @Operation(summary = "Eliminar conferencista", description = "Elimina un conferencista del sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Conferencista eliminado exitosamente", content = {
          @Content(mediaType = "application/json")
      }),
      @ApiResponse(responseCode = "400", description = "No existe un conferencista con el ID proporcionado", content = {
          @Content(mediaType = "application/json")
      })
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteConferencista(@PathVariable Long id) {
    try {
      conferencistaService.eliminarConferencista(id);
      return ResponseEntity.ok(Map.of("response", "Conferencista con id " + id + " eliminado exitosamente"));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("response", e.getMessage()));
    }
  }
}