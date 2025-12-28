package com.threads.auth.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
