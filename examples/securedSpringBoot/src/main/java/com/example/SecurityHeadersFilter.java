package com.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    )throws ServletException, IOException {

        // Header pour protection XSS
        // demande au navigateur de bloquer les pages détectées comme contenant du XSS.
        response.setHeader("X-XSS-Protection", "1; mode=block");

        // Header pour empêcher le framing (Clickjacking)
        // empêche que l’API soit chargée dans un <iframe>, protection contre
        // clickjacking.
        response.setHeader("X-Frame-Options", "DENY");

        // Header pour Content Security Policy basique
        // restreint l’origine des scripts et ressources à 'self' pour réduire le risque
        // XSS.
        response.setHeader("Content-Security-Policy", "default-src 'self'");

        // Vérification simple du header Authorization pour toutes les requêtes /api/secured
        String path = request.getRequestURI();
        if (path.startsWith("/api/secured")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
