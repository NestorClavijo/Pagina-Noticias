package com.app.noticias.controller;

import com.app.noticias.DTO.CrearNoticiaRequest;
import com.app.noticias.Service.NoticiaService;
import com.app.noticias.model.Noticia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Este controlador maneja las operaciones relacionadas con las noticias.
   Utiliza el servicio NoticiaService para crear, leer, actualizar y eliminar noticias. */
@RestController
@RequestMapping("/api/Noticia/") // Define la ruta base para todas las operaciones de noticias (por ejemplo: "/api/Noticia/crear")
@RequiredArgsConstructor // Lombok genera automáticamente un constructor para inyectar las dependencias necesarias
@CrossOrigin(origins="*") // Permite el acceso desde cualquier origen para este controlador
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService; // Inyección de dependencia del servicio NoticiaService para manejar las noticias.

    /*
    @Operation resumen: Crear una nueva noticia.
    @Parameter describe el parámetro 'noticiaRequest' que contiene los datos necesarios para crear la noticia.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Crear una nueva noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia creada con éxito")
    })
    @PostMapping("/crear") // Define la ruta y el método HTTP (POST) para crear una nueva noticia
    public ResponseEntity<Noticia> create(
            @Parameter(description = "Datos necesarios para crear una noticia") @RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.crearNoticia(
                noticiaRequest.getUsuarioId(),
                noticiaRequest.getTitulo(),
                noticiaRequest.getDescripcion()));
        // Llama al servicio NoticiaService para crear la noticia y devuelve una respuesta con el objeto creado.
    }

    /*
    @Operation resumen: Obtener todas las noticias de un usuario específico.
    @Parameter describe el parámetro 'usuarioId' como el ID del usuario para obtener sus noticias.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Obtener noticias de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticias obtenidas con éxito")
    })
    @GetMapping("/usuario/{usuarioId}") // Define la ruta y el método HTTP (GET) para obtener las noticias de un usuario
    public ResponseEntity<List<Noticia>> getNoticiasByUsuario(
            @Parameter(description = "ID del usuario para obtener sus noticias") @PathVariable Long usuarioId) {

        return ResponseEntity.ok(noticiaService.noticiasUsuario(usuarioId));
        // Llama al servicio NoticiaService para obtener las noticias del usuario y las devuelve con un código 200 OK.
    }

    /*
    @Operation resumen: Actualizar una noticia existente.
    @Parameter describe el parámetro 'noticiaRequest' que contiene los nuevos datos de la noticia.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Actualizar una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia actualizada con éxito")
    })
    @PutMapping("/actualizar/{id}") // Define la ruta y el método HTTP (PUT) para actualizar una noticia
    public ResponseEntity<Noticia> update(
            @Parameter(description = "ID de la noticia que se desea actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo título de la noticia") @RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.actualizarNoticia(id, noticiaRequest.getTitulo(), noticiaRequest.getDescripcion()));
        // Llama al servicio NoticiaService para actualizar la noticia y devuelve la respuesta con la noticia actualizada.
    }

    /*
    @Operation resumen: Eliminar una noticia existente.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Eliminar una noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia eliminada con éxito")
    })
    @DeleteMapping("/eliminar/{id}") // Define la ruta y el método HTTP (DELETE) para eliminar una noticia
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la noticia que se desea eliminar") @PathVariable Long id) {

        noticiaService.deleteNoticia(id);
        return ResponseEntity.noContent().build();
        // Llama al servicio NoticiaService para eliminar la noticia y responde con un código 204 No Content.
    }
}
