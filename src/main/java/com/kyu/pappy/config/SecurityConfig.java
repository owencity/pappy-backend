package com.kyu.pappy.config;

import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.security.JwtFilter;
import com.kyu.pappy.security.JwtUtil;
import com.kyu.pappy.security.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, UserRepository userRepository
    ) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http
//                .csrf((auth) -> auth.disable());
//
//        http
//                .formLogin((auth) -> auth.disable());
//
//        http
//                .httpBasic((auth) -> auth.disable());
//
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN") // admin 요청은 ADMIN 역할이 있어야 허용
//                        .anyRequest().authenticated()); // 그 외 요청은 모두 허용
//
//        http
//                .addFilterBefore(new JwtFilter(jwtUtil, userRepository), LoginFilter.class);
//        // JwtFilter를 UsernamePasswordAuthenticationFilter 전에 추가
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
//        // LoginFilter를 UsernamePasswordAuthenticationFilter 전에 추가
//        // CustomLogoutFilter를 LogoutFilter 전에 추가
//
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));	// 세션 관리 설정: 상태가 없는 세션을 사용하도록 설정합니다.
//
//        return http.build();
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN") // admin 요청은 ADMIN 역할이 있어야 허용
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtUtil, userRepository), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 상태 없는 세션
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // CORS 설정 적용

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // 클라이언트 도메인
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // 인증 정보 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}