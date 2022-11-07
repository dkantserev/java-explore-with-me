package ru.practicum.errorApi.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.errorApi.ApiError;



public class ClientNoConnectionException extends RuntimeException {

    public ClientNoConnectionException(String message) {
        super(message);
    }

    public ApiError get() {
        String reason = "bad request";
        return new ApiError(getMessage(), reason, HttpStatus.CONFLICT.toString());
    }
}
