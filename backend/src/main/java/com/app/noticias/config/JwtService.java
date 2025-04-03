package com.app.noticias.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* Esta clase proporciona métodos para generar, validar y extraer información de un token JWT.
   Utiliza la biblioteca `io.jsonwebtoken` para crear y verificar tokens firmados. */
@Service
public class JwtService {

    // La clave secreta utilizada para firmar los tokens JWT
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /* Este método genera un token JWT para un usuario basado en sus detalles.
       Llama al método privado `getToken` con un mapa vacío de "extra claims" (reclamaciones adicionales). */
    public String getToken(UserDetails usuario) {
        return getToken(new HashMap<>(), usuario);
    }

    /* Este método genera un token JWT usando una clave secreta y un conjunto de "extra claims".
       El token tiene un sujeto (el nombre de usuario), una fecha de emisión y una fecha de expiración. */
    private String getToken(Map<String, Object> extraClaims, UserDetails usuario) {
        return Jwts
                .builder()
                // Se establecen las reclamaciones adicionales (si las hay)
                .setClaims(extraClaims)
                // Establece el nombre de usuario como sujeto del token
                .setSubject(usuario.getUsername())
                // Fecha de emisión del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Fecha de expiración del token (1 día de validez)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                // Firma el token usando la clave secreta y el algoritmo HS256
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact(); // Genera el token JWT
    }

    /* Este método devuelve la clave secreta que se utiliza para firmar los tokens JWT.
       La clave se obtiene a partir de una cadena codificada en Base64. */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // Genera la clave HMAC para la firma del token
    }

    /* Este método extrae el nombre de usuario desde el token JWT. */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject); // Obtiene el "subject" del token (el nombre de usuario)
    }

    /* Este método valida el token JWT comprobando si el nombre de usuario coincide y si el token no ha expirado. */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /* Este método obtiene todas las reclamaciones del token JWT (como el nombre de usuario y la fecha de expiración). */
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey()) // Usa la clave secreta para verificar la firma
                .build()
                .parseClaimsJws(token) // Analiza el JWT
                .getBody(); // Devuelve las reclamaciones (información contenida en el token)
    }

    /* Este método obtiene una reclamación específica del token JWT usando un `claimsResolver`.
       `claimsResolver` es una función que define cómo extraer la reclamación deseada (por ejemplo, el nombre de usuario). */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token); // Obtiene todas las reclamaciones
        return claimsResolver.apply(claims); // Aplica la función para obtener la reclamación específica
    }

    /* Este método verifica si el token JWT ha expirado comparando la fecha de expiración con la fecha actual. */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date()); // Si la fecha de expiración es anterior a la fecha actual, el token ha expirado
    }

    /* Este método obtiene la fecha de expiración del token JWT. */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration); // Obtiene la fecha de expiración de las reclamaciones del token
    }
}
