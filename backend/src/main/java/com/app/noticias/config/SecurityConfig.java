package com.app.noticias.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/* Esta clase se encarga de la configuración de seguridad de la aplicación.
 La anotación @Configuration indica que es una clase de configuración de Spring.
 @EnableWebSecurity habilita la configuración de seguridad web en la aplicación.*/
@Configuration
@EnableWebSecurity
/* @RequiredArgsConstructor genera automáticamente un constructor con los parámetros finales necesarios,
 en este caso, jwtAuthenticationFilter y authProvider.*/
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtros y proveedores de autenticación necesarios para manejar la seguridad.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    // El bean SecurityFilterChain configura las reglas de seguridad para las rutas.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                // Desactiva la protección CSRF (Cross-Site Request Forgery) ya que la aplicación no maneja formularios.
                .csrf(AbstractHttpConfigurer::disable)
                // Define las reglas de autorización para las rutas de la aplicación.
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                /* Permite el acceso sin autenticación a las rutas que comienzan con "/auth/**"
                                 y las necesarias para swagger, que son las rutas de login, registro y Swagger.*/
                                // Las demás rutas requieren autenticación.
                                .requestMatchers("/swagger.html","/swagger-ui/*", "/swagger-ui.html", "/docs/", "/v3/api-docs/*","/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                // Configura la aplicación para que no use sesiones HTTP, esto es útil en una arquitectura sin estado.
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Establece un proveedor de autenticación personalizado.
                .authenticationProvider(authProvider)
                // Añade el filtro JWT para manejar la autenticación de los usuarios.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Finalmente, construye la configuración de seguridad.
                .build();
    }

    /* Configura CORS (Cross-Origin Resource Sharing) para permitir que las solicitudes desde diferentes orígenes
     accedan a la API.*/
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Permite que todos los orígenes, métodos y cabeceras accedan a la API.
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);  // Permite que las credenciales (cookies, encabezados de autenticación) se incluyan.

        // Registra la configuración CORS para todas las rutas de la aplicación.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
