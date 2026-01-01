package com.threads.dto;

import com.threads.entities.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {
    private Long id;
    private String firstName;
    private String lastName;
}
