package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un modelo de entidad que representa la calificación de un comentario dentro del sistema.
   Utiliza anotaciones de JPA para mapearla a una tabla en la base de datos y de Lombok para generar
   constructores y métodos automáticamente.
*/
@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "calificacion_comentario") // Mapea esta entidad a la tabla "calificacion_comentario" en la base de datos
public class CalificacionComentario {

    /*
       ID de la calificación, generado automáticamente por la base de datos.
       Este es el identificador único para cada calificación de comentario.
    */
    @Id // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos generará el valor automáticamente.
    private Long id_calificacion;

    /*
       Valor de la calificación del comentario. No puede ser nulo.
       Este es el puntaje asignado al comentario.
    */
    @Column(name = "valor", nullable = false) // Mapea este campo a la columna "valor" en la tabla.
    private Integer valor;

    /*
       Usuario que realiza la calificación.
       Este es el objeto que representa al usuario que califica el comentario.
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con un solo usuario.
    @JoinColumn(name = "usuario_id") // Mapea la relación con la tabla "usuario" a través de la columna "usuario_id".
    private Usuario usuario;

    /*
       Comentario que está siendo calificado.
       Este es el objeto que representa el comentario que recibe la calificación.
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con un solo comentario.
    @JoinColumn(name = "comentario_id") // Mapea la relación con la tabla "comentario" a través de la columna "comentario_id".
    private Comentario comentario;
}
