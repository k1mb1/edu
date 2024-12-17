package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import me.k1mb.edu.repository.model.Lesson;

import java.util.UUID;

/**
 * DTO response для {@link Lesson}
 */
@Schema(description = "DTO ответа с информацией об уроке")
public record LessonResponse(
    @Schema(description = "Уникальный идентификатор урока",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED)
    UUID id,

    @Schema(description = "Идентификатор курса, к которому относится урок",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED)
    UUID courseId,

    @Schema(description = "Название урока",
        example = "Введение в программирование",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String title,

    @Schema(description = "Описание урока",
        example = "В этом уроке вы узнаете основы программирования",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Продолжительность урока в минутах",
        example = "45",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Integer duration) {
}