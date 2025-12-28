package com.threads.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiError {
    private String error;
    private String path;
    private String method;
    private LocalDateTime timeStamp;
}
