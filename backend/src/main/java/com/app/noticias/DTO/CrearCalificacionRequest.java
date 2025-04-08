package com.app.noticias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar los datos
   necesarios para crear o actualizar la calificación de una noticia.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class CrearCalificacionRequest {

    /*
       ID del usuario que realiza la calificación.
       Este es el identificador único del usuario que está calificando la noticia.
    */
    private Long usuarioId;

    /*
       ID de la noticia que está siendo calificada.
       Este es el identificador único de la noticia a la que se le asignará la calificación.
    */
    private Long noticiaId;

    /*
       Valor de la calificación que se asigna a la noticia.
       Este es el valor de la calificación, que generalmente puede ser un número entero, por ejemplo, del 1 al 5.
    */
    private Integer valor;
}

