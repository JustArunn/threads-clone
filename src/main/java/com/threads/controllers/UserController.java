package com.threads.controllers;

import com.threads.dto.UserDTO;
import com.threads.dto.response.ApiResponse;
import com.threads.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> profile(Authentication authentication){
        UserDTO user = userService.getProfile(authentication);
        return new ResponseEntity<>(new ApiResponse(
                "Profile fetched successfully",
                user,
                HttpStatus.OK),
                HttpStatus.OK);
    }
}
