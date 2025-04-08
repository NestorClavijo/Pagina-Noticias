package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) que se utiliza para enviar los datos
   necesarios para buscar la calificación de una noticia por el ID de la noticia y el ID del usuario.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class BuscarCalificacionRequest {

    /*
       ID de la noticia que se va a calificar o buscar la calificación.
       Este es el identificador único de la noticia para la cual se quiere obtener la calificación.
    */
    private Long noticiaId;

    /*
       ID del usuario que realizó la calificación.
       Este es el identificador único del usuario cuya calificación se desea obtener.
    */
    private Long usuarioId;
}

