package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import me.k1mb.edu.repository.entity.CourseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * DTO response для {@link CourseEntity}
 */
@Schema(description = "DTO ответа с информацией о курсе")
public record CourseResponse(
    @Schema(description = "Уникальный идентификатор курса",
        requiredMode = REQUIRED)
    UUID id,

    @Schema(description = "Название курса",
        example = "Основы программирования",
        requiredMode = REQUIRED)
    String title,

    @Schema(description = "Описание курса",
        example = "Курс по основам программирования на языке Java",
        requiredMode = REQUIRED)
    String description,

    @Schema(description = "Идентификатор автора курса",
        requiredMode = REQUIRED)
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