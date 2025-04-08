package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
   Esta clase representa una noticia dentro del sistema. Una noticia es creada por un usuario y tiene un título,
   una descripción y una fecha y hora de publicación.
   Utiliza anotaciones de JPA para mapearla a una tabla en la base de datos y de Lombok para generar
   constructores y métodos automáticamente.
*/
@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "noticia") // Mapea esta entidad a la tabla "noticia" en la base de datos
public class Noticia {

    /*
       ID de la noticia, generado automáticamente por la base de datos.
       Este es el identificador único para cada noticia en el sistema.
    */
    @Id // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos generará el valor automáticamente.
    private Long id_noticia;

    /*
       Título de la noticia. No puede ser nulo y su longitud está limitada a 255 caracteres.
       Este es el encabezado o título principal de la noticia.
    */
    @Column(name = "titulo", length = 255, nullable = false) // Mapea este campo a la columna "titulo" en la tabla.
    private String titulo;

    /*
       Descripción de la noticia, almacenada en formato "TEXT".
       Este es el contenido completo o texto detallado de la noticia.
    */
    @Column(name = "texto_noticia", columnDefinition = "TEXT") // Mapea este campo a la columna "texto_noticia" en la tabla.
    private String descripcion;

    /*
       Fecha y hora de publicación de la noticia. No puede ser nulo.
       Este campo almacena la fecha y hora exacta de cuando la noticia fue publicada.
    */
    @Column(name = "fecha_hora", nullable = false) // Mapea este campo a la columna "fecha_hora" en la tabla.
    private LocalDateTime fecha_hora;

    /*
       Usuario que creó la noticia. Este campo establece la relación con la entidad "Usuario".
       Una noticia está asociada a un usuario que la publicó.
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con un solo usuario.
    @JoinColumn(name = "usuario_id") // Mapea la relación con la tabla "usuario" a través de la columna "usuario_id".
    private Usuario usuario;
}
