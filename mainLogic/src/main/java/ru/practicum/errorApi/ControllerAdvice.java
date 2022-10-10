package ru.practicum.errorApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.errorApi.exception.*;


@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ApiError> RequestNotFoundFound(RequestNotFoundException e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiError> handleEventNotFound(EventNotFoundException e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompilationNotFound.class)
    public ResponseEntity<ApiError> handleCompilationNotFound(CompilationNotFound e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFound e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ApiError> handleLocationNotFound(LocationNotFoundException e) {
        return new ResponseEntity<>(e.get(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException e) {
        var x = e.get();
        x.setErrors(e.getStackTrace());
        return new ResponseEntity<>(x, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNoConnectionException.class)
    public ResponseEntity<ApiError> handleClientNoConnection(ClientNoConnectionException e) {
        var x = e.get();
        x.setErrors(e.getStackTrace());
        return new ResponseEntity<>(x, HttpStatus.CONFLICT);
    }
}
