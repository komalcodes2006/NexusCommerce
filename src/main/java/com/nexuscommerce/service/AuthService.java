package com.nexuscommerce.service;

import com.nexuscommerce.dto.AuthResponse;
import com.nexuscommerce.dto.LoginRequest;
import com.nexuscommerce.dto.RegisterRequest;
import com.nexuscommerce.entity.User;
import com.nexuscommerce.entity.UserRole;
import com.nexuscommerce.repository.UserRepository;
import com.nexuscommerce.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        user.setPassword(hashedPassword);

        user.setRole(UserRole.CUSTOMER);

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        // Step A: Find the user in the database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Step B: Verify the password
        // .matches() automatically hashes the raw password and compares it to the database hash
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Step C: Generate the JWT
        String token = jwtTokenProvider.generateToken(user.getEmail());

        // Step D: Return the formatted AuthResponse
        return new AuthResponse(token, "Bearer");
    }
}
