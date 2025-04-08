package com.app.noticias.DTOsecurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
   Esta clase es un DTO (Data Transfer Object) utilizado para enviar la respuesta
   de autenticación, que contiene el token JWT y el ID del usuario.
*/
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
public class AuthResponse {

    /*
       Token de autenticación JWT generado para el usuario después de un login exitoso.
       Este token se utiliza para autenticar las solicitudes del usuario en las siguientes peticiones.
    */
    private String token;

    /*
       ID del usuario al que se le ha asignado el token de autenticación.
       Este identificador se utiliza para identificar al usuario en el sistema.
    */
    private long id;
}
