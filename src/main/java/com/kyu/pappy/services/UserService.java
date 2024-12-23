package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.user.UserAuthenticationResponse;
import com.kyu.pappy.repositories.UserRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return  userRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
    }

    public UserAuthenticationResponse authenticate(String username, String password) {
        var user =
                userRepository
                        .findByUserEmail(username)
                        .orElseThrow(() -> new UserNotFoundException(username));
    if(passwordEncoder.matches(password, user.getPassword())) {
        var accessToken = jwtService.generateAccessToken(user);
        return new UserAuthenticationResponse(accessToken);
    }
    }
}
