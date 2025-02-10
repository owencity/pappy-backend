package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.model.user.UserDeleteRequest;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(UserDeleteRequest userDeleteRequest, Authentication auth) {
        CustomUserDetails findUser = (CustomUserDetails) auth.getPrincipal();
        String currentUser = findUser.getUsername();

        User user = userRepository.findByUserEmail(currentUser).orElseThrow(() -> new UserNotFoundException(currentUser));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new CustomUserDetails(user);
    }


    }

