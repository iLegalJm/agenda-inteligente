package com.agenda.agenda_inteligente.infrastructure.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("🔍 Filtro JWT - Path: " + request.getRequestURI());
        System.out.println("🔍 Filtro JWT - Method: " + request.getMethod());

        final String header = request.getHeader("Authorization");
        System.out.println("🔍 Authorization Header: " + header);

        // Si no hay header de autorización, continúa sin autenticar
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("⚠️ No hay token Bearer, continuando sin autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        System.out.println("🔑 Token extraído: " + token.substring(0, Math.min(token.length(), 20)) + "...");

        // Validar el token
        if (jwtProvider.esTokenValido(token)) {
            System.out.println("✅ Token válido");

            String email = jwtProvider.getEmail(token);
            String rol = jwtProvider.getRol(token);

            System.out.println("👤 Email: " + email);
            System.out.println("👤 Rol: " + rol);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(() -> "ROLE_" + rol));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("✅ Autenticación establecida en SecurityContext");
            filterChain.doFilter(request, response);
        } else {
            // Token inválido - rechazar la petición
            System.out.println("❌ Token inválido, rechazando petición");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{\"status\": 401, \"message\": \"Token JWT inválido o expirado\", \"data\": null}");
            response.getWriter().flush();

            // NO llamar a filterChain.doFilter() - detener aquí
        }
    }
}