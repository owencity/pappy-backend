package com.kyu.pappy.config;

import com.kyu.pappy.handlers.JwtHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {

   private final JwtHandshakeInterceptor jwtHandShakeInterceptor;

    public StompConfiguration(JwtHandshakeInterceptor jwtHandShakeInterceptor) {
        this.jwtHandShakeInterceptor = jwtHandShakeInterceptor;
    }


    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chats")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        // 클라이언트가 SEND 할때 /pub 로 시작하는 모든 경로는 MessageMapping과 매칭됨
        // 실제로 컨트롤러에서는 /pub이 자동으로 제거된 상태로 처리
        registry.enableSimpleBroker("/sub");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtHandShakeInterceptor);
    }


}
