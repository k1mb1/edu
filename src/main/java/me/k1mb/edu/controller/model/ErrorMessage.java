package me.k1mb.edu.controller.model;

import java.time.LocalDateTime;

public record ErrorMessage(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path) {
}
