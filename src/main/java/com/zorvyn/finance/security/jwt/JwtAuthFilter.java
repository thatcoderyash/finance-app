package com.zorvyn.finance.security.jwt;

import com.zorvyn.finance.models.User;
import com.zorvyn.finance.repository.UserRepository;
import com.zorvyn.finance.utils.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final UserDetailsService userDetailsService;

    private void handleJwtException(HttpServletResponse response, Exception ex) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String message;

        if (ex.getMessage().contains("expired")) {
            message = "Token expired";
        } else if (ex.getMessage().contains("invalid")) {
            message = "Invalid token";
        } else {
            message = "Authentication failed";
        }

        String json = String.format(
                "{\"message\": \"%s\", \"status\": %d, \"timestamp\": %d}",
                message,
                401,
                System.currentTimeMillis()
        );

        response.getWriter().write(json);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        log.info("incoming request: {}", request.getRequestURI());

        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = requestTokenHeader.substring(7); // cleaner
            String email = authUtil.getEmailFromToken(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (authUtil.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {

            // 👉 VERY IMPORTANT: Don't crash, send proper response
            handleJwtException(response, ex);
        }
    }
}
