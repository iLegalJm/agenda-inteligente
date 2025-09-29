package com.agenda.agenda_inteligente.infrastructure.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    // Implementación del proveedor de JWT
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un token JWT para el correo electrónico y rol especificados.
     *
     * @param email el correo electrónico del usuario para quien se genera el token
     * @param rol   el rol asignado al usuario
     * @return un token JWT firmado que contiene el correo electrónico y el rol del
     *         usuario como claims
     */
    public String generarToken(String email, String rol, long expirationMs) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Valida el token JWT proporcionado y analiza sus claims.
     *
     * @param token el token JWT a validar y analizar
     * @return un objeto {@link Jws} que contiene los claims si el token es válido
     * @throws io.jsonwebtoken.security.SecurityException si la firma del token es
     *                                                    inválida
     * @throws io.jsonwebtoken.MalformedJwtException      si el token está mal
     *                                                    formado
     * @throws io.jsonwebtoken.ExpiredJwtException        si el token está expirado
     * @throws io.jsonwebtoken.UnsupportedJwtException    si el token no es
     *                                                    soportado
     * @throws IllegalArgumentException                   si el token es nulo o
     *                                                    vacío
     */
    public Jws<Claims> validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public boolean esTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // Token inválido o expirado
            return false;
        }
    }

    public String getEmail(String token) {
        return validarToken(token).getBody().getSubject();
    }

    public String getRol(String token) {
        return (String) validarToken(token).getBody().get("rol");
    }
}
