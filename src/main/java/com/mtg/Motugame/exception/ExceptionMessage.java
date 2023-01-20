package com.mtg.Motugame.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    public final static String NO_DATA_ERROR = "no such data";
    public final static String USER_ALREADY_EXISTS = "user already exists!";
    public final static String INVALID_START_REQUEST = "start must not be less than 0";
    public final static String INVALID_END_REQUEST = "end must be greater than or equal to start.";
}
