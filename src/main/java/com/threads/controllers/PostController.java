package com.threads.controllers;

import com.threads.dto.PostDTO;
import com.threads.dto.request.CreatePostRequest;
import com.threads.dto.response.ApiResponse;
import com.threads.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping({"/", ""})
    public ResponseEntity<ApiResponse> createPost(@RequestBody CreatePostRequest request, Authentication authentication){
        String message = postService.createPost(request, authentication);

        return new ResponseEntity<>(
                new ApiResponse(message,
                        null,
                        HttpStatus.CREATED)
                ,HttpStatus.CREATED);

    }

    @GetMapping({"/", ""})
    public ResponseEntity<ApiResponse> getAllPosts(){
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(
                new ApiResponse("All posts fetched successfully",
                        posts,
                        HttpStatus.OK),
                HttpStatus.OK);
    }
}
