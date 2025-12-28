package com.threads.auth.controller;

import com.threads.auth.dto.response.AuthResponse;
import com.threads.auth.service.JwtService;
import com.threads.dto.UserDTO;
import com.threads.auth.dto.request.LoginRequest;
import com.threads.auth.dto.request.SignupRequest;
import com.threads.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>signup(@RequestBody SignupRequest request){
        UserDTO user = userService.signup(request);
        String token = jwtService.generateToken(user.getUsername());
        return new ResponseEntity<>(
                new AuthResponse("Account created successfully",
                        token,
                        user,
                        HttpStatus.CREATED),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest request){
        UserDTO user = userService.login(request);
        String token = jwtService.generateToken(user.getUsername());
        return new ResponseEntity<>(
                new AuthResponse(
                "Account logged in successfully",
                token,
                user,
                HttpStatus.OK),
                HttpStatus.OK);
    }
}
