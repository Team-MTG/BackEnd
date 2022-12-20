package com.mtg.Motugame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class ApiException {
    private final String msg;
    private final HttpStatus httpStatus;
}
