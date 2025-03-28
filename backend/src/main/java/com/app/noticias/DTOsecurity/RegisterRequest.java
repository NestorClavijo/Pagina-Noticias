package com.app.noticias.DTOsecurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    Long user_id;
    String apellido;
    String descripcion;
    String email;
    String nombre;
    String telefono;
    String username;
    String password;
}
