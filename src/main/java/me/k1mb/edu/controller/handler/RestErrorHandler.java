package me.k1mb.edu.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.k1mb.edu.controller.model.ErrorMessage;
import me.k1mb.edu.service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(
        Exception ex,
        HttpServletRequest request) {

        log.error(ex.getMessage());
        return new ResponseEntity<>(
            new ErrorMessage(
                LocalDateTime.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.name(),
                ex.getMessage(),
                request.getRequestURI()),
            INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(
        ResourceNotFoundException ex,
        HttpServletRequest request) {

        log.error(ex.getMessage());
        return new ResponseEntity<>(
            new ErrorMessage(
                LocalDateTime.now(),
                NOT_FOUND.value(),
                NOT_FOUND.name(),
                ex.getMessage(),
                request.getRequestURI()),
            NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex,
        HttpServletRequest request) {

        log.error(ex.getMessage());
        return new ResponseEntity<>(
            new ErrorMessage(
                LocalDateTime.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.name(),
                ex.getMessage(),
                request.getRequestURI()),
            BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex,
        HttpServletRequest request) {

        log.error(ex.getMessage());
        return new ResponseEntity<>(
            new ErrorMessage(
                LocalDateTime.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.name(),
                ex.getMessage(),
                request.getRequestURI()),
            BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleMethodAccessDeniedException(
        AccessDeniedException ex,
        HttpServletRequest request) {

        log.error(ex.getMessage());
        return new ResponseEntity<>(
            new ErrorMessage(
                LocalDateTime.now(),
                FORBIDDEN.value(),
                FORBIDDEN.name(),
                ex.getMessage(),
                request.getRequestURI()),
            FORBIDDEN);
    }
}
