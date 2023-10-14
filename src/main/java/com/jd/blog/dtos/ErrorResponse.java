package com.jd.blog.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
        private int code;
        private String message;
        private String details;
        private LocalDateTime timestamp;
}
