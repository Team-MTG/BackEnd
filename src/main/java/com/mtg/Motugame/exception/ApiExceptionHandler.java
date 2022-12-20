package com.mtg.Motugame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        if(e.getMessage().equals(ExceptionMessage.USER_ALREADY_EXISTS)){
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

            ApiException apiException = new ApiException(
                    ExceptionMessage.USER_ALREADY_EXISTS,
                    httpStatus);

            return new ResponseEntity<>(apiException, httpStatus);
        }

        HttpStatus httpStatus =HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ExceptionMessage.NO_DATA_ERROR,
                httpStatus);

        return new ResponseEntity<>(apiException,httpStatus);
    }
}
