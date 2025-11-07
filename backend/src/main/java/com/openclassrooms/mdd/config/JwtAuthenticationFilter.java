package com.openclassrooms.mdd.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.openclassrooms.mdd.service.JwtService;
import com.openclassrooms.mdd.service.user.UserService;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_SPACE = "Bearer ";

    private final JwtService jwtService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Processing request: {} {}", request.getMethod(), request.getRequestURI());

        /* Check Authorization header */
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_SPACE)) {
            log.warn("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            log.info("Valid Authorization header found");
            /* Extract JWT from the Authorization header */
            final String jwt = authHeader.substring(BEARER_SPACE.length()).trim();
            final String userUuid = jwtService.extractUserUuid(jwt);
            log.info("Extracted user UUID: {}", userUuid);

            /* If userUUID is not null and no authentication is present, validate the JWT */
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (StringUtils.isNotBlank(userUuid) && Objects.isNull(authentication)) {
                /* Check if the JWT is valid */
                if (jwtService.isTokenValid(jwt)) {
                    setSecurityContextAuthentication(request, userUuid);
                } else {
                    log.warn("Invalid JWT for user: {} - Token expired: {}", userUuid, jwtService.isTokenExpired(jwt));
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            log.error("Exception occurred while processing JWT: {}", exception.getMessage(), exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    /**
     * Sets the authentication in the SecurityContext based on the provided user UUID.
     *
     * @param request the HttpServletRequest to extract details from
     * @param userUuid the UUID of the user to authenticate
     */
    private void setSecurityContextAuthentication(HttpServletRequest request, String userUuid) {
        /* Create an authentication token and set it in the SecurityContext */
        UserDetails userDetails = this.userService.loadUserByUsername(userUuid);
        log.info("Valid JWT - Setting authentication for user UUID: {}", userUuid);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
