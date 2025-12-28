package com.threads.auth.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthError {
    private String error;
    private String path;
    private String method;
    private LocalDateTime timeStamp;
    private HttpStatus status;
}
