package me.k1mb.edu.config;

import jakarta.servlet.http.HttpServletRequest;
import me.k1mb.edu.exeption.ErrorMessage;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
            LocalDateTime.now(),
            INTERNAL_SERVER_ERROR.value(),
            INTERNAL_SERVER_ERROR.name(),
            ex.getMessage(),
            request.getRequestURI());
        return new ResponseEntity<>(errorMessage, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(
        ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
            LocalDateTime.now(),
            NOT_FOUND.value(),
            NOT_FOUND.name(),
            ex.getMessage(),
            request.getRequestURI());
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
            LocalDateTime.now(),
            BAD_REQUEST.value(),
            BAD_REQUEST.name(),
            ex.getMessage(),
            request.getRequestURI());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
            LocalDateTime.now(),
            BAD_REQUEST.value(),
            BAD_REQUEST.name(),
            ex.getMessage(),
            request.getRequestURI());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }
}
