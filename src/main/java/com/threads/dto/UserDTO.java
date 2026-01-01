package com.threads.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<PostDTO> posts;
    private Long followersCount;
    private Long followingsCount;
    private List<FollowDTO>followers;
    private List<FollowDTO>followings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
