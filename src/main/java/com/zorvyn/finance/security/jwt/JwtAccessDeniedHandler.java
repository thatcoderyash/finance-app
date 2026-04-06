package com.zorvyn.finance.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.@NonNull AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String json = String.format(
                "{\"message\": \"%s\", \"status\": %d, \"path\": \"%s\", \"timestamp\": %d}",
                "Access Denied",
                403,
                request.getRequestURI(),
                System.currentTimeMillis()
        );

        response.getWriter().write(json);
    }
}
