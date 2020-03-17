package com.repostit.repostit.Exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class RepostitException extends RuntimeException{
    public RepostitException(String message) {
        super(message);
    }
}
