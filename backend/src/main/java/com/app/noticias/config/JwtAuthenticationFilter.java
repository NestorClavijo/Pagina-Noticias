package com.app.noticias.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* Esta clase es un filtro personalizado para manejar la autenticación basada en JWT (JSON Web Token).
   Extiende de OncePerRequestFilter para garantizar que se ejecute solo una vez por solicitud.
   La anotación @Component permite que Spring gestione esta clase como un bean y lo registre en el contexto de la aplicación. */
@Component
@RequiredArgsConstructor // Lombok genera un constructor automáticamente con las dependencias necesarias
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Dependencias necesarias para validar el token JWT y cargar los detalles del usuario
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /* Este método es el núcleo del filtro y se encarga de verificar el token en cada solicitud HTTP.
       Si el token es válido, se autentica al usuario. */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el token JWT de la solicitud
        final String token = getTokenFromRequest(request);
        final String username;

        // Si no hay token, pasa la solicitud al siguiente filtro
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtiene el nombre de usuario a partir del token
        username = jwtService.getUsernameFromToken(token);

        /* Si el nombre de usuario es válido y no hay una autenticación previa en el contexto de seguridad,
           se valida el token y se establece la autenticación en el SecurityContext. */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario desde la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Si el token es válido, crea un token de autenticación y lo establece en el contexto de seguridad
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Establece detalles adicionales sobre la autenticación, como la IP del cliente
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece el token de autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pasa la solicitud al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

    /* Este método extrae el token JWT de los encabezados de la solicitud.
       Busca el encabezado 'Authorization' y extrae el token si está presente. */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si el encabezado contiene un token válido, lo devuelve sin el prefijo "Bearer"
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null; // Si no se encuentra el token, retorna null
    }
}
