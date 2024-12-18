package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import me.k1mb.edu.repository.entity.CourseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO response для {@link CourseEntity}
 */
@Schema(description = "DTO ответа с информацией о курсе")
public record CourseResponse(
    @Schema(description = "Уникальный идентификатор курса",
        requiredMode = RequiredMode.REQUIRED)
    UUID id,

    @Schema(description = "Название курса",
        example = "Основы программирования",
        requiredMode = RequiredMode.REQUIRED)
    String title,

    @Schema(description = "Описание курса",
        example = "Курс по основам программирования на языке Java",
        requiredMode = RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Идентификатор автора курса",
        requiredMode = RequiredMode.REQUIRED)
    UUID authorId,

    @Schema(description = "Дата и время создания курса",
        example = "2023-10-01T12:34:56",
        requiredMode = RequiredMode.NOT_REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Дата и время последнего обновления курса",
        example = "2023-10-05T14:20:30",
        requiredMode = RequiredMode.NOT_REQUIRED)
    LocalDateTime updatedAt) {
}