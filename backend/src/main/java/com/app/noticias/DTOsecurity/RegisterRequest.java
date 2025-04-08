package com.app.noticias.DTOsecurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar los datos
   necesarios para registrar un nuevo usuario en el sistema.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class RegisterRequest {

    /*
       ID del usuario, utilizado como identificador único del nuevo usuario.
       Este campo es necesario para crear una nueva entrada de usuario en la base de datos.
    */
    private Long user_id;

    /*
       Apellido del usuario, utilizado para completar los datos personales del usuario.
       Este es un campo que contiene el apellido del usuario.
    */
    private String apellido;

    /*
       Descripción adicional sobre el usuario, que puede contener información adicional o biografía.
    */
    private String descripcion;

    /*
       Correo electrónico del usuario, utilizado como un medio de contacto y para la autenticación.
       Este campo debe ser único y validado para garantizar la correcta creación de la cuenta.
    */
    private String email;

    /*
       Nombre del usuario, utilizado para almacenar el primer nombre del usuario.
    */
    private String nombre;

    /*
       Teléfono del usuario, utilizado para contacto directo o verificación adicional.
    */
    private String telefono;

    /*
       Nombre de usuario para acceder a la aplicación, utilizado para la autenticación del usuario.
       Este campo debe ser único dentro del sistema.
    */
    private String username;

    /*
       Contraseña del usuario, utilizada para autenticar al usuario durante el inicio de sesión.
       Este campo debe ser validado y encriptado antes de almacenarse.
    */
    private String password;
}
