package com.app.noticias.DTOsecurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar los datos
   necesarios para realizar una solicitud de inicio de sesión (login).
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
public class LoginRequest {

    /*
       Nombre de usuario que se utiliza para autenticar al usuario en el sistema.
       Este es el identificador único del usuario dentro de la aplicación.
    */
    private String Username;

    /*
       Contraseña del usuario que se utiliza junto con el nombre de usuario para autenticarlo.
       Este campo debe ser validado antes de permitir el acceso a la aplicación.
    */
    private String password;
}
