package com.threads.services;

import com.threads.dto.FollowDTO;
import com.threads.entities.FollowEntity;
import com.threads.entities.UserEntity;
import com.threads.repositories.FollowRepository;
import com.threads.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public String followUser(Long followingId, Authentication authentication) {
        UserEntity a = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new RuntimeException("Unauthorize : User not found"));

        if (a.getId().equals(followingId)) {
            throw new RuntimeException("User cannot follow themselves");
        }

        UserEntity b = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        if (followRepository.existsByFollowerAndFollowing(a, b)) {
            return a.getFirstName() + " already following " + b.getFirstName();
        }

        FollowEntity follow = FollowEntity.builder()
                .follower(a)
                .following(b)
                .createdAt(LocalDateTime.now())
                .build();

        followRepository.save(follow);
        return a.getFirstName() + " is now following " + b.getFirstName();
    }

    public Long getFollowerCount(UserEntity user){
        return followRepository.countByFollowing(user);
    }

    public Long getFollowingCount(UserEntity user){
        return followRepository.countByFollower(user);
    }

    public List<FollowDTO> getFollowers(UserEntity user) {
        return followRepository.findByFollowing(user) // who follows ME
                .stream()
                .map(follow -> new FollowDTO(
                        follow.getFollower().getId(),
                        follow.getFollower().getFirstName(),
                        follow.getFollower().getLastName()
                ))
                .toList();
    }

    public List<FollowDTO> getFollowing(UserEntity user) {
        return followRepository.findByFollower(user) // who I follow
                .stream()
                .map(follow -> new FollowDTO(
                        follow.getFollowing().getId(),
                        follow.getFollowing().getFirstName(),
                        follow.getFollowing().getLastName()
                ))
                .toList();
    }

    public List<FollowDTO> getFollowersByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollowing(user)
                .stream()
                .map(follow -> new FollowDTO(
                        follow.getFollower().getId(),
                        follow.getFollower().getFirstName(),
                        follow.getFollower().getLastName()
                ))
                .toList();
    }


    public List<FollowDTO> getFollowingsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollower(user)
                .stream()
                .map(follow -> new FollowDTO(
                        follow.getFollowing().getId(),
                        follow.getFollowing().getFirstName(),
                        follow.getFollowing().getLastName()
                ))
                .toList();
    }
}
