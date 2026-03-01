package com.micro.auth.controller;



import com.micro.security.jwt.JwtService;
import com.micro.user.entity.User;
import com.micro.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        User user = userRepository
                .findByUsername(request.get("username"))
                .orElseThrow();

        if (!passwordEncoder.matches(
                request.get("password"),
                user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "token", token
        ));
    }
}