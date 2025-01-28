package com.kyu.pappy.handlers;

import com.kyu.pappy.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class JwtHandshakeInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
   public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 핸드쉐이크 이후 연결되면 가공된 헤더를 추출불가 -> 인터셉트하여 연결되기전 토큰 추출 및 검증

       StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
       if (StompCommand.CONNECT.equals(accessor.getCommand())) {

           String token = Optional.ofNullable(accessor.getFirstNativeHeader("Authorization"))
                           .map(authHeader -> authHeader.replace("Bearer " , "").trim())
                                   .orElseThrow(() -> new IllegalArgumentException("authorization header is missing"));

           if(!jwtUtil.validToken(token)) {
               return null;
           }
           // 사숑자 정보 추출 및 세션 저장
           String username = Optional.ofNullable(jwtUtil.getUsername(token))
                   .orElseThrow(() -> new IllegalArgumentException("Invalid token: missing username"));

           accessor.getSessionAttributes().put("username", username);
       }
       return message;
   }


}
