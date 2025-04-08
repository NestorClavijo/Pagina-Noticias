package com.app.noticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/*
   Esta clase representa una comunidad dentro del sistema. Una comunidad está formada por un conjunto de noticias
   y es creada por un usuario. Utiliza anotaciones de JPA para mapearla a una tabla en la base de datos y de Lombok
   para generar constructores y métodos automáticamente.
*/
@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "comunidad") // Mapea esta entidad a la tabla "comunidad" en la base de datos
public class Comunidad {

    /*
       ID de la comunidad, generado automáticamente por la base de datos.
       Este es el identificador único para cada comunidad en el sistema.
    */
    @Id // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos generará el valor automáticamente.
    private Long id_comunidad;

    /*
       Título de la comunidad. No puede ser nulo y su longitud está limitada a 255 caracteres.
       Este es el nombre o encabezado de la comunidad.
    */
    @Column(name = "titulo", length = 255, nullable = false) // Mapea este campo a la columna "titulo" en la tabla.
    private String titulo;

    /*
       Tema de la comunidad. No puede ser nulo y su longitud está limitada a 255 caracteres.
       Este es el tema principal o categoría que representa la comunidad.
    */
    @Column(name = "tema", length = 255, nullable = false) // Mapea este campo a la columna "tema" en la tabla.
    private String tema;

    /*
       Descripción de la comunidad, almacenada en formato "TEXT".
       Este campo contiene información adicional sobre la comunidad, como sus objetivos o actividades.
    */
    @Column(name = "descripcion", columnDefinition = "TEXT") // Mapea este campo a la columna "descripcion" en la tabla.
    private String descripcion;

    /*
       Usuario que crea la comunidad. Este campo establece la relación con la entidad "Usuario".
       Cada comunidad es creada por un usuario específico.
    */
    @ManyToOne // Indica que muchos registros de esta clase pueden estar relacionados con un solo usuario.
    @JoinColumn(name = "usuario_id") // Mapea la relación con la tabla "usuario" a través de la columna "usuario_id".
    private Usuario usuario;

    /*
       Noticias asociadas a la comunidad. Esta es una relación de muchos a muchos con la entidad Noticia.
       Una comunidad puede tener muchas noticias relacionadas.
    */
    @ManyToMany // Relación muchos a muchos con la entidad Noticia.
    @JoinTable(
            name = "comunidad_noticia", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_comunidad"), // Columna que mapea la relación con la comunidad
            inverseJoinColumns = @JoinColumn(name = "id_noticia") // Columna que mapea la relación con la noticia
    )
    private Set<Noticia> noticias;
}
