package com.threads.controllers;

import com.threads.dto.CommentDTO;
import com.threads.dto.request.CreateCommentRequest;
import com.threads.dto.response.ApiResponse;
import com.threads.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse> createComment(@PathVariable("postId") Long postId, @RequestBody CreateCommentRequest request, Authentication authentication){
        CommentDTO comment = commentService.createComment(authentication, postId, request);

        return new ResponseEntity<>(
                new ApiResponse("Comment added to Post successfully", comment, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getMyComments(Authentication authentication){
        List<CommentDTO> comments = commentService.getMyComments(authentication);
        return new ResponseEntity<>(
                new ApiResponse("My comments fetched successfully", comments, HttpStatus.OK
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostComments(@PathVariable("postId") Long postId){
        List<CommentDTO> comments = commentService.getPostComments(postId);
        return new ResponseEntity<>(
                new ApiResponse("Post comments fetched successfully", comments, HttpStatus.OK
                ),
                HttpStatus.OK
        );
    }
}
