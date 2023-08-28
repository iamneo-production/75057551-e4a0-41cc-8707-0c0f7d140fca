package com.loanservice.apigateway.auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;



@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private static final String SECRET_KEY = "your-secret-key"; // Replace with your actual secret key

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractTokenFromRequest(exchange.getRequest());

        if (token != null && isValidToken(token)) {
            Claims claims = extractClaimsFromToken(token);
            String username = claims.getSubject(); // Assuming the subject contains the username

            if (hasPermission(username, claims)) {
                // User is authenticated and authorized
                return chain.filter(exchange);
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    private boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean hasPermission(String username, Claims claims) {
        // Implement permission checks based on roles/claims in the token
        // For example:
        // String role = claims.get("role", String.class);
        // return "admin".equals(role);
        return true; // For demonstration purposes
    }


   /* @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }*/
}
