package com.app.noticias.controller;

import com.app.noticias.DTO.CrearNoticiaRequest;
import com.app.noticias.Service.NoticiaService;
import com.app.noticias.model.Noticia;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/* Este controlador maneja las operaciones relacionadas con las noticias.
   Utiliza el servicio NoticiaService para crear, leer, actualizar y eliminar noticias. */
@RestController
@RequestMapping("/api/Noticia/") // Define la ruta base para todas las operaciones relacionadas con las noticias (por ejemplo: "/api/Noticia/crear")
@RequiredArgsConstructor // Lombok genera automáticamente un constructor para inyectar las dependencias necesarias
@CrossOrigin(origins="*") // Permite el acceso desde cualquier origen para este controlador
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService; // Inyección de dependencia del servicio NoticiaService para manejar las noticias.

    /*
    @Operation: Crea una nueva noticia utilizando los datos proporcionados en el cuerpo de la solicitud.
    @Parameter: Describe los parámetros de la solicitud que incluyen los datos de la noticia.
    */
    @CrossOrigin(origins="*")
    @PostMapping("/crear") // Define la ruta y el método HTTP (POST) para crear una nueva noticia
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Crear una nueva noticia",
            description = "Crea una noticia con los datos proporcionados, incluyendo el usuario, título y descripción."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia creada con éxito")
    })
    public ResponseEntity<Noticia> create(
            @Parameter(description = "Datos necesarios para crear la noticia") @RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.crearNoticia(
                noticiaRequest.getUsuarioId(),
                noticiaRequest.getTitulo(),
                noticiaRequest.getDescripcion()));
        // Llama al servicio NoticiaService para crear la noticia y devuelve una respuesta con el objeto creado.
    }

    /*
    @Operation: Obtiene las noticias asociadas a un usuario específico basado en su ID.
    @Parameter: Describe el parámetro usuarioId que es el identificador del usuario.
    */
    @CrossOrigin(origins="*")
    @GetMapping("/usuario/{usuarioId}") // Define la ruta y el método HTTP (GET) para obtener las noticias de un usuario
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Obtener noticias de un usuario",
            description = "Recupera todas las noticias asociadas a un usuario específico mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticias obtenidas con éxito")
    })
    public ResponseEntity<List<Noticia>> getNoticiasByUsuario(
            @Parameter(description = "ID del usuario para obtener sus noticias") @PathVariable Long usuarioId) {

        return ResponseEntity.ok(noticiaService.noticiasUsuario(usuarioId));
        // Llama al servicio NoticiaService para obtener las noticias del usuario y las devuelve con un código 200 OK.
    }

    /*
    @Operation: Actualiza una noticia existente proporcionando el ID de la noticia y los nuevos datos.
    @Parameter: Describe el parámetro id (ID de la noticia) y noticiaRequest (nuevos datos de la noticia).
    */
    @CrossOrigin(origins="*")
    @PutMapping("/actualizar/{id}") // Define la ruta y el método HTTP (PUT) para actualizar una noticia
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Actualizar una noticia existente",
            description = "Permite actualizar una noticia existente utilizando su ID y los nuevos datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia actualizada con éxito")
    })
    public ResponseEntity<Noticia> update(
            @Parameter(description = "ID de la noticia que se desea actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevo título y descripción de la noticia") @RequestBody CrearNoticiaRequest noticiaRequest) {

        return ResponseEntity.ok(noticiaService.actualizarNoticia(id, noticiaRequest.getTitulo(), noticiaRequest.getDescripcion()));
        // Llama al servicio NoticiaService para actualizar la noticia y devuelve la respuesta con la noticia actualizada.
    }

    /*
    @Operation: Elimina una noticia existente utilizando su ID.
    @Parameter: Describe el parámetro id (ID de la noticia).
    */
    @CrossOrigin(origins="*")
    @DeleteMapping("/eliminar/{id}") // Define la ruta y el método HTTP (DELETE) para eliminar una noticia
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Eliminar una noticia",
            description = "Elimina una noticia existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Noticia eliminada con éxito")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la noticia que se desea eliminar") @PathVariable Long id) {

        noticiaService.deleteNoticia(id);
        return ResponseEntity.noContent().build();
        // Llama al servicio NoticiaService para eliminar la noticia y responde con un código 204 No Content.
    }
}
