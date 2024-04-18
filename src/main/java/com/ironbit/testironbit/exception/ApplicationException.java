package com.ironbit.testironbit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private String errorCode;
    private String message;
    private HttpStatus httpStatus;

}
