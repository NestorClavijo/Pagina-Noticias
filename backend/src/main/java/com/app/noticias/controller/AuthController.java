package com.app.noticias.controller;

import com.app.noticias.DTOsecurity.AuthResponse;
import com.app.noticias.DTOsecurity.LoginRequest;
import com.app.noticias.DTOsecurity.RegisterRequest;
import com.app.noticias.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* Este controlador maneja las solicitudes de autenticación de los usuarios, como el login y el registro.
   Utiliza el servicio AuthService para autenticar a los usuarios y registrar nuevos usuarios. */
@RestController
@RequestMapping("/auth") // Define la ruta base para todas las operaciones de autenticación (por ejemplo: "/auth/login", "/auth/register")
@RequiredArgsConstructor // Lombok genera automáticamente un constructor para inyectar las dependencias necesarias
@CrossOrigin(origins="*") // Permite el acceso desde cualquier origen
public class AuthController {

    private final AuthService authService; // Inyección de dependencia del servicio AuthService

    /*
    @Operation resumen: Iniciar sesión de un usuario.
    @Parameter describe el parámetro 'request' que contiene las credenciales de inicio de sesión.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Login de usuario", description = "Permite al usuario iniciar sesión con sus credenciales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping(value = "/login") // Define la ruta y el método HTTP (POST) para el login
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request // Aquí se recibe el cuerpo de la solicitud con los datos de login
    ) {
        return ResponseEntity.ok(authService.login(request)); // Llama al servicio AuthService para realizar el login y devuelve la respuesta
    }

    /*
    @Operation resumen: Registrar un nuevo usuario en el sistema.
    @Parameter describe el parámetro 'request' que contiene los datos de registro del usuario.
    @ApiResponses define las respuestas posibles para este endpoint, solo se maneja el código de respuesta 200.
    */
    @Operation(summary = "Registro de usuario", description = "Permite registrar un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping(value = "/register") // Define la ruta y el método HTTP (POST) para el registro
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request // Aquí se recibe el cuerpo de la solicitud con los datos del nuevo usuario
    ) {
        return ResponseEntity.ok(authService.register(request)); // Llama al servicio AuthService para registrar al usuario y devuelve la respuesta
    }
}
