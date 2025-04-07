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

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                // Habilitar CORS usando la configuración definida en corsConfigurationSource()
                .cors().and()
                // Desactivar CSRF ya que no usamos formularios clásicos
                .csrf(AbstractHttpConfigurer::disable)
                // Configurar la autorización para las rutas
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/swagger.html","/swagger-ui/", "/swagger-ui.html", "/docs/", "/v3/api-docs/","/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                // Configurar la gestión de sesiones para que sea sin estado
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Usamos AllowedOriginPatterns para permitir todos los orígenes con credenciales activadas
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // O, mejor, especifica el origen de tu front, por ejemplo:
        // corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
