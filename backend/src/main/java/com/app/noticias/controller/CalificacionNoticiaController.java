package com.app.noticias.controller;

import com.app.noticias.DTO.BuscarCalificacionRequest;
import com.app.noticias.DTO.CrearCalificacionRequest;
import com.app.noticias.Service.LikeService;
import com.app.noticias.model.CalificacionNoticia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/* Este controlador maneja las operaciones relacionadas con las calificaciones de noticias.
   Utiliza el servicio LikeService para crear, leer, actualizar y eliminar las calificaciones de noticias. */
@RestController
@RequestMapping("/api/calificacionNoticia") // Define la ruta base para todas las operaciones de calificación (por ejemplo: "/api/calificacionNoticia/crear")
@RequiredArgsConstructor // Lombok genera automáticamente un constructor para inyectar las dependencias necesarias
@CrossOrigin(origins="*") // Permite el acceso desde cualquier origen para este controlador
public class CalificacionNoticiaController {

    @Autowired
    private LikeService likeService; // Inyección de dependencia del servicio LikeService para manejar las calificaciones.

    /*
    @Operation resumen: Crear una nueva calificación para una noticia.
    @Parameter describe los parámetros de la solicitud como el ID del usuario, el ID del comentario y el valor de la calificación.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Crear una nueva calificación para una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación creada con éxito")
    })
    @PostMapping("/crear") // Define la ruta y el método HTTP (POST) para crear la calificación
    public ResponseEntity<CalificacionNoticia> create(
            @Parameter(description = "Datos necesarios para calificar un comentario") @RequestBody CrearCalificacionRequest calificacionRequest) {

        return ResponseEntity.ok(likeService.crearCalificacion(
                calificacionRequest.getNoticiaId(),
                calificacionRequest.getUsuarioId(),
                calificacionRequest.getValor()));
        // Llama al servicio LikeService para crear la calificación y devuelve una respuesta con el objeto creado.
    }

    /*
    @Operation resumen: Obtener una calificación de una noticia específica.
    @Parameter describe los parámetros 'usuarioId' y 'comentarioId' para obtener la calificación relacionada.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Obtener una calificación de una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación obtenida con éxito")
    })
    @PostMapping("/buscar") // Define la ruta y el método HTTP (POST) para obtener una calificación
    public ResponseEntity<CalificacionNoticia> read(
            @Parameter(description = "Datos necesarios para obtener una calificacion") @RequestBody BuscarCalificacionRequest calificacionRequest) {

        Optional<CalificacionNoticia> calificacion = likeService.obtenerCalificacion(
                calificacionRequest.getNoticiaId(),
                calificacionRequest.getUsuarioId());
        return calificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        // Si la calificación existe, la devuelve; si no, responde con un error 404.
    }

    /*
    @Operation resumen: Actualizar una calificación existente.
    @Parameter describe el parámetro 'nuevoValor' que será el nuevo valor de la calificación.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Actualizar una calificación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación actualizada con éxito")
    })
    @PutMapping("/actualizar/{id}") // Define la ruta y el método HTTP (PUT) para actualizar la calificación
    public ResponseEntity<CalificacionNoticia> update(
            @Parameter(description = "ID de la calificación que se desea actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo valor de la calificación") @RequestBody CrearCalificacionRequest calificacionRequest) {

        return ResponseEntity.ok(likeService.cambiarCalificacion(id, calificacionRequest.getValor()));
        // Llama al servicio LikeService para actualizar la calificación y devuelve la respuesta con la calificación actualizada.
    }

    /*
    @Operation resumen: Eliminar una calificación existente.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Eliminar una calificación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación eliminada con éxito")
    })
    @DeleteMapping("/eliminar/{id}") // Define la ruta y el método HTTP (DELETE) para eliminar la calificación
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la calificación que se desea eliminar") @PathVariable Long id) {

        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
        // Llama al servicio LikeService para eliminar la calificación y responde con un código 204 No Content.
    }
}
