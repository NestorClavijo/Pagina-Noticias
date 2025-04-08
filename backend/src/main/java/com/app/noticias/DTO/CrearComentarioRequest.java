package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar los datos
   necesarios para crear o modificar un comentario sobre una noticia.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class CrearComentarioRequest {

    /*
       ID del usuario que realiza el comentario.
       Este es el identificador único del usuario que está comentando sobre la noticia.
    */
    private Long usuarioId;

    /*
       ID de la noticia sobre la cual se va a realizar el comentario.
       Este es el identificador único de la noticia a la que se le añadirá el comentario.
    */
    private Long noticiaId;

    /*
       Descripción del comentario.
       Este es el texto que el usuario deja como comentario en la noticia.
    */
    private String descripcion;
}
