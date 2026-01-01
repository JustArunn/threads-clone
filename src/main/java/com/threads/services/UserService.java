package com.threads.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threads.dto.CommentDTO;
import com.threads.dto.FollowDTO;
import com.threads.dto.PostDTO;
import com.threads.dto.UserDTO;
import com.threads.auth.dto.request.LoginRequest;
import com.threads.auth.dto.request.SignupRequest;
import com.threads.entities.UserEntity;
import com.threads.repositories.FollowRepository;
import com.threads.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;
    private final ObjectMapper objectMapper;

    public UserDTO signup(SignupRequest request){
        Boolean isExistingUser = userRepository.existsByUsername(request.getUsername());
        if(isExistingUser){
            throw new RuntimeException("User already exists by this username: " + request.getUsername());
        }
        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(request.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        UserEntity savedUser =  userRepository.save(user);
        return objectMapper.convertValue(savedUser, UserDTO.class);
    }

    public UserDTO login(LoginRequest request){
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        return this.profile(user.getId());
    }

    public UserDTO me(Authentication authentication){
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new RuntimeException("User does not exists by this username: " + authentication.getName()));

        Long followersCount = followService.getFollowerCount(user);
        Long followingCount = followService.getFollowingCount(user);
        List<FollowDTO> followers = followService.getFollowers(user);
        List<FollowDTO> followings = followService.getFollowing(user);

        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .followersCount(followersCount)
                .followingsCount(followingCount)
                .followers(followers)
                .followings(followings)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .posts(user.getPosts().stream()
                        .map(post -> PostDTO.builder()
                                .id(post.getId())
                                .userId(post.getUser().getId())
                                .comments(post.getComments()
                                        .stream().map(comment -> CommentDTO.builder()
                                                .id(comment.getId())
                                                .postId(comment.getPost().getId())
                                                .userId(comment.getUser().getId())
                                                .username(comment.getUser().getUsername())
                                                .comment(comment.getComment())
                                                .createdAt(comment.getCreatedAt())
                                                .updatedAt(comment.getUpdatedAt())
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public UserDTO profile(Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User does not exists by this username: " + userId));

        Long followersCount = followRepository.countByFollower(user);
        Long followingCount = followRepository.countByFollowing(user);

       return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .followersCount(followersCount)
                .followingsCount(followingCount)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .posts(user.getPosts().stream()
                        .map(post -> PostDTO.builder()
                                .id(post.getId())
                                .userId(post.getUser().getId())
                                .comments(post.getComments()
                                        .stream().map(comment -> CommentDTO.builder()
                                                .id(comment.getId())
                                                .postId(comment.getPost().getId())
                                                .userId(comment.getUser().getId())
                                                .username(comment.getUser().getUsername())
                                                .comment(comment.getComment())
                                                .createdAt(comment.getCreatedAt())
                                                .updatedAt(comment.getUpdatedAt())
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
