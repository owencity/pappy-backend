package com.kyu.pappy.handlers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Value("${spring.jwt.secret}")
    private String jwtSecret;




    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes ) throws Exception


    {
       String authHeader = request.getHeaders().getFirst("Authorization");
       if (authHeader != null && authHeader.startsWith("Bearer ")) {
           String token = authHeader.substring(7);
           try {
               validateToken(token);
           } catch (Exception e) {
               response.setStatusCode(HttpStatus.UNAUTHORIZED);
               return false;
           }
       } else {
           response.setStatusCode(HttpStatus.UNAUTHORIZED);
           return false;
       }
       return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private void validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parseClaimsJws(token); // 예외가 발생하면 유효하지 않은 토큰
    }
}
