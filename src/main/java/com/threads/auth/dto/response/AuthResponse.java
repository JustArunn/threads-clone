package com.threads.auth.dto.response;

import com.threads.dto.UserDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private UserDTO user;
    private HttpStatus status;
}
