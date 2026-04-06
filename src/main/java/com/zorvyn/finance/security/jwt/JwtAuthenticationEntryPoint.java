package com.zorvyn.finance.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException
    ) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = String.format(
                "{\"message\": \"%s\", \"status\": %d, \"path\": \"%s\", \"timestamp\": %d}",
                "Unauthorized: " + authException.getMessage(),
                401,
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        response.getWriter().write(json);
    }
}
