package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import me.k1mb.edu.repository.model.Course;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO response для {@link Course}
 */
@Schema(description = "DTO ответа с информацией о курсе")
public record CourseResponse(
    @Schema(description = "Уникальный идентификатор курса",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED)
    UUID id,

    @Schema(description = "Название курса",
        example = "Основы программирования",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String title,

    @Schema(description = "Описание курса",
        example = "Курс по основам программирования на языке Java",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Идентификатор автора курса",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED)
    UUID authorId,

    @Schema(description = "Дата и время создания курса",
        example = "2023-10-01T12:34:56",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Дата и время последнего обновления курса",
        example = "2023-10-05T14:20:30",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    LocalDateTime updatedAt) {
}