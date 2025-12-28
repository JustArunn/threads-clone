package com.threads.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threads.dto.CommentDTO;
import com.threads.dto.request.CreateCommentRequest;
import com.threads.entities.CommentEntity;
import com.threads.entities.PostEntity;
import com.threads.entities.UserEntity;
import com.threads.repositories.CommentRepository;
import com.threads.repositories.PostRepository;
import com.threads.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ObjectMapper objectMapper;

    public CommentDTO createComment(Authentication authentication, Long postId, CreateCommentRequest request) {
        UserEntity user = (userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found with this ID: " + authentication.getName())));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(()->new RuntimeException("Post not found with this ID: " + postId));

        CommentEntity comment = CommentEntity.builder()
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();

        CommentEntity savedComment = commentRepository.save(comment);
        return CommentDTO.builder()
                .id(savedComment.getId())
                .comment(savedComment.getComment())
                .userId(savedComment.getUser().getId())
                .username(savedComment.getUser().getUsername())
                .postId(savedComment.getPost().getId())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

    public List<CommentDTO> getMyComments(Authentication authentication){
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new RuntimeException("User not found!"));

        List<CommentEntity> comments = commentRepository.findAllByUserId(user.getId());
        return comments.stream().map(c -> CommentDTO.builder()
                .id(c.getId())
                .comment(c.getComment())
                .userId(c.getUser().getId())
                .username(c.getUser().getUsername())
                .postId(c.getPost().getId())
                .updatedAt(c.getUpdatedAt())
                .createdAt(c.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

    public List<CommentDTO> getPostComments(Long postId) {
        if(!postRepository.existsById(postId)){
            throw new RuntimeException("Post not found!");
        }
        List<CommentEntity> comments = commentRepository.findAllCommentsOnPost(postId);
        return comments.stream().map(c -> CommentDTO.builder()
                .id(c.getId())
                .postId(c.getPost().getId())
                .comment(c.getComment())
                .userId(c.getUser().getId())
                .username(c.getUser().getUsername())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build()).collect(Collectors.toList());
    }
}
