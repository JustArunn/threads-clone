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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
