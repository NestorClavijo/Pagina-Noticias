package com.app.noticias.model;

import com.app.noticias.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/*
   Esta clase representa a un usuario dentro del sistema. Implementa la interfaz UserDetails de Spring Security
   para integrar la autenticación y autorización en el sistema.
*/
@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "usuario") // Mapea esta entidad a la tabla "usuario" en la base de datos
public class Usuario implements UserDetails {

    /*
       ID del usuario, generado automáticamente por la base de datos.
       Este es el identificador único para cada usuario en el sistema.
    */
    @Id // Marca este campo como la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de datos generará el valor automáticamente.
    private Long id_usuario;

    /*
       Nombre del usuario. Este es el primer nombre del usuario y no puede ser nulo.
    */
    @Column(name = "nombre", length = 255, nullable = false) // Mapea este campo a la columna "nombre" en la tabla.
    private String nombre;

    /*
       Apellido del usuario. Este es el apellido del usuario y no puede ser nulo.
    */
    @Column(name = "apellido", length = 255, nullable = false) // Mapea este campo a la columna "apellido" en la tabla.
    private String apellido;

    /*
       Correo electrónico del usuario. Este campo debe ser único y no puede ser nulo.
    */
    @Column(name = "email", length = 255, nullable = false, unique = true) // Mapea este campo a la columna "email" en la tabla.
    private String email;

    /*
       Teléfono del usuario. Este es el número de teléfono del usuario y no puede ser nulo.
    */
    @Column(name = "telefono", length = 10, nullable = false) // Mapea este campo a la columna "telefono" en la tabla.
    private String telefono;

    /*
       Descripción del usuario. Este campo contiene información adicional sobre el usuario y se almacena como texto.
    */
    @Column(name = "descripcion", columnDefinition = "TEXT") // Mapea este campo a la columna "descripcion" en la tabla.
    private String descripcion;

    /*
       Puntos acumulados por el usuario por los comentarios realizados. Este campo puede ser nulo.
    */
    @Column(name = "puntos_comentario") // Mapea este campo a la columna "puntos_comentario" en la tabla.
    private Float puntos_comentario;

    /*
       Puntos acumulados por el usuario por las noticias que ha publicado. Este campo puede ser nulo.
    */
    @Column(name = "puntos_noticia") // Mapea este campo a la columna "puntos_noticia" en la tabla.
    private Float puntos_noticia;

    /*
       Nombre de usuario utilizado para la autenticación. Este campo debe ser único y no puede ser nulo.
    */
    @Column(name = "username", length = 255, nullable = false, unique = true) // Mapea este campo a la columna "username" en la tabla.
    private String username;

    /*
       Contraseña del usuario. Este campo es necesario para la autenticación del usuario y no puede ser nulo.
    */
    @Column(name = "password", length = 255, nullable = false) // Mapea este campo a la columna "password" en la tabla.
    private String password;

    /*
       Rol del usuario en el sistema. Este campo se mapea a la enumeración `Role` y define los permisos del usuario.
    */
    @Enumerated(EnumType.STRING) // Mapea el campo `role` con la enumeración `Role` y lo guarda como una cadena.
    private Role role;

    /*
       Comunidades a las que el usuario pertenece. Esta es una relación de muchos a muchos con la entidad Comunidad.
    */
    @ManyToMany // Relación muchos a muchos con la entidad Comunidad.
    @JoinTable(
            name = "Comunidad_Usuario", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_usuario"), // Columna que mapea la relación con el usuario
            inverseJoinColumns = @JoinColumn(name = "id_comunidad") // Columna que mapea la relación con la comunidad
    )
    private Set<Comunidad> comunidades;

    // Métodos

    /*
       Método necesario para la implementación de la interfaz UserDetails de Spring Security.
       Este método devuelve una lista vacía de autoridades (roles) para el usuario.
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Devuelve una lista vacía de autoridades, ya que no se asignan roles específicos aquí.
    }
}
