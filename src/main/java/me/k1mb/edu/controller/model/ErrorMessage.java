package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Структура сообщения об ошибке")
public record ErrorMessage(
    @Schema(description = "Временная метка ошибки", example = "2023-10-01T12:34:56")
    LocalDateTime timestamp,

    @Schema(description = "HTTP-статус ошибки", example = "404")
    int status,

    @Schema(description = "Тип ошибки", example = "Not Found")
    String error,

    @Schema(description = "Описание ошибки", example = "Ресурс не найден")
    String message,

    @Schema(description = "Путь, по которому произошла ошибка", example = "/api/v1/courses")
    String path) {
}