package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
   Esta clase representa un comentario realizado por un usuario sobre una noticia.
   Utiliza anotaciones de JPA para mapearla a una tabla en la base de datos y de Lombok para generar
   constructores y métodos automáticamente.
*/
@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "comentario") // Mapea esta entidad a la tabla "comentario" en la base de datos
public class Comentario {

    /*
       ID del comentario, generado automáticamente por la base de datos.
       Este es el identificador único para cada comentario en el sistema.
    */
    @Id // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos generará el valor automáticamente.
    private Long id_comentario;

    /*
       Texto del comentario, almacenado en formato "TEXT".
       Este es el contenido escrito por el usuario en el comentario.
    */
    @Column(name = "texto_comentario", columnDefinition = "TEXT") // Mapea este campo a la columna "texto_comentario" en la tabla.
    private String descripcion;

    /*
       Fecha y hora en que el comentario fue realizado. No puede ser nulo.
       Este campo almacena la fecha y hora exacta de cuando se hizo el comentario.
    */
    @Column(name = "fecha_hora", nullable = false) // Mapea este campo a la columna "fecha_hora" en la tabla.
    private LocalDateTime fecha_hora;

    /*
       Noticia relacionada con el comentario. Un comentario está asociado a una noticia específica.
       Este campo establece la relación con la entidad "Noticia".
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con una sola noticia.
    @JoinColumn(name = "noticia_id") // Mapea la relación con la tabla "noticia" a través de la columna "noticia_id".
    private Noticia noticia;

    /*
       Usuario que realiza el comentario. Este campo establece la relación con la entidad "Usuario".
       Un comentario está asociado a un usuario específico.
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con un solo usuario.
    @JoinColumn(name = "usuario_id") // Mapea la relación con la tabla "usuario" a través de la columna "usuario_id".
    private Usuario usuario;
}
