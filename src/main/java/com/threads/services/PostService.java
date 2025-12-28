package com.threads.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threads.dto.CommentDTO;
import com.threads.dto.PostDTO;
import com.threads.dto.request.CreatePostRequest;
import com.threads.entities.PostEntity;
import com.threads.entities.UserEntity;
import com.threads.repositories.PostRepository;
import com.threads.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public List<PostDTO> getAllPosts(){
        List<PostEntity>posts = postRepository.findAll();

        return posts.stream().map(post -> PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userId(post.getUser().getId())      // Only include ID
                .username(post.getUser().getUsername()) // Optional
                .comments(post.getComments().stream().map(c -> CommentDTO.builder()
                        .id(c.getId())
                        .postId(c.getPost().getId())
                        .comment(c.getComment())
                        .userId(c.getUser().getId())
                        .username(c.getUser().getUsername())
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .build()
                ).collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
    }

    public PostDTO createPost(CreatePostRequest request, Authentication authentication){

        Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());

        if(user.isEmpty()){
            throw new RuntimeException("User not found with this username: " + authentication.getName());
        }

        if(request.getContent().isBlank()){
            throw new RuntimeException("Post content can't ne empty.");
        }

        PostEntity post = PostEntity.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user.get())
                .comments(Collections.emptyList())
                .build();

        PostEntity savedPost = postRepository.save(post);

        return objectMapper.convertValue(savedPost, PostDTO.class);
    }
}
