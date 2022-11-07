package ru.practicum.errorApi.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.errorApi.ApiError;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(String message) {
        super(message);
    }

    public ApiError get() {
        String reason = "The required object was not found.";
        return new ApiError(getMessage(), reason, HttpStatus.NOT_FOUND.toString());
    }
}
