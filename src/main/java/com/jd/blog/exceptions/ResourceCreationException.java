package com.jd.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceCreationException extends RuntimeException {
    public ResourceCreationException(String message, Exception ex) {
        super(message, ex);
    }
}