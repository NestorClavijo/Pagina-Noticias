package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar los datos
   necesarios para crear o modificar una noticia.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class CrearNoticiaRequest {

    /*
       ID del usuario que está creando la noticia.
       Este es el identificador único del usuario que está enviando los datos para la creación de una nueva noticia.
    */
    private Long usuarioId;

    /*
       Título de la noticia.
       Este es el título o encabezado de la noticia que se está creando.
    */
    private String titulo;

    /*
       Descripción de la noticia.
       Este es el contenido o descripción detallada de la noticia que se va a publicar.
    */
    private String descripcion;
}
