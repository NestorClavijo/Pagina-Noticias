package com.app.noticias.controller;

import com.app.noticias.DTO.CrearComentarioRequest;
import com.app.noticias.Service.ComentarioService;
import com.app.noticias.model.Comentario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Este controlador maneja las operaciones relacionadas con los comentarios de las noticias.
   Utiliza el servicio ComentarioService para crear, leer, actualizar y eliminar comentarios. */
@RestController
@RequestMapping("/api/Comentario/") // Define la ruta base para todas las operaciones de comentarios (por ejemplo: "/api/Comentario/crear")
@RequiredArgsConstructor // Lombok genera automáticamente un constructor para inyectar las dependencias necesarias
@CrossOrigin(origins="*") // Permite el acceso desde cualquier origen para este controlador
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService; // Inyección de dependencia del servicio ComentarioService para manejar los comentarios.

    /*
    @Operation resumen: Crear un nuevo comentario para una noticia.
    @Parameter describe el parámetro 'comentarioRequest' que contiene los datos necesarios para crear el comentario.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Crear un nuevo comentario para una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario creado con éxito")
    })
    @PostMapping("/crear") // Define la ruta y el método HTTP (POST) para crear un nuevo comentario
    public ResponseEntity<Comentario> create(
            @Parameter(description = "Datos necesarios para crear un comentario") @RequestBody CrearComentarioRequest comentarioRequest) {

        return ResponseEntity.ok(comentarioService.crearComentario(
                comentarioRequest.getUsuarioId(),
                comentarioRequest.getNoticiaId(),
                comentarioRequest.getDescripcion()));
        // Llama al servicio ComentarioService para crear el comentario y devuelve una respuesta con el objeto creado.
    }

    /*
    @Operation resumen: Obtener todos los comentarios para una noticia específica.
    @Parameter describe el parámetro 'noticiaId' como el ID de la noticia para obtener sus comentarios.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Obtener comentarios de una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos con éxito")
    })
    @GetMapping("/noticia/{noticiaId}") // Define la ruta y el método HTTP (GET) para obtener los comentarios de una noticia
    public ResponseEntity<List<Comentario>> getComentariosByNoticia(
            @Parameter(description = "ID de la noticia para obtener sus comentarios") @PathVariable Long noticiaId) {
        return ResponseEntity.ok(comentarioService.comentarioNoticia(noticiaId));
        // Llama al servicio ComentarioService para obtener los comentarios de la noticia y los devuelve con un código 200 OK.
    }

    /*
    @Operation resumen: Actualizar un comentario existente.
    @Parameter describe el parámetro 'comentarioRequest' que contiene el nuevo contenido del comentario.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Actualizar un comentario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario actualizado con éxito")
    })
    @PutMapping("/actualizar/{id}") // Define la ruta y el método HTTP (PUT) para actualizar un comentario
    public ResponseEntity<Comentario> update(
            @Parameter(description = "ID del comentario que se desea actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo contenido del comentario") @RequestBody CrearComentarioRequest comentarioRequest) {

        return ResponseEntity.ok(comentarioService.actualizarcomentario(id, comentarioRequest.getDescripcion()));
        // Llama al servicio ComentarioService para actualizar el comentario y devuelve la respuesta con el comentario actualizado.
    }

    /*
    @Operation resumen: Eliminar un comentario existente.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Eliminar un comentario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario eliminado con éxito")
    })
    @DeleteMapping("/eliminar/{id}") // Define la ruta y el método HTTP (DELETE) para eliminar un comentario
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del comentario que se desea eliminar") @PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
        // Llama al servicio ComentarioService para eliminar el comentario y responde con un código 204 No Content.
    }
}
