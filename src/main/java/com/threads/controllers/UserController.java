package com.threads.controllers;

import com.threads.dto.FollowDTO;
import com.threads.dto.UserDTO;
import com.threads.dto.response.ApiResponse;
import com.threads.services.FollowService;
import com.threads.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> me(Authentication authentication){
        UserDTO user = userService.me(authentication);
        return new ResponseEntity<>(new ApiResponse(
                "My profile fetched successfully",
                user,
                HttpStatus.OK),
                HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> me(@PathVariable("userId") Long userId){
        UserDTO user = userService.profile(userId);
        return new ResponseEntity<>(new ApiResponse(
                "Profile fetched successfully",
                user,
                HttpStatus.OK),
                HttpStatus.OK);
    }

    @PostMapping("/follow/{toFollowId}")
    public ResponseEntity<ApiResponse>followUser(@PathVariable("toFollowId") Long toFollowId, Authentication authentication){
        String message = followService.followUser(toFollowId, authentication);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message(message)
                        .data(null)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<ApiResponse> getFollowers(@PathVariable("userId") Long userId){
        List<FollowDTO> followers = followService.getFollowersByUserId(userId);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Followers fetched successfully")
                        .data(followers)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/followings/{userId}")
    public ResponseEntity<ApiResponse> getFollowings(@PathVariable("userId") Long userId){
        List<FollowDTO> followings = followService.getFollowingsByUserId(userId);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Followings fetched successfully")
                        .data(followings)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }
}
