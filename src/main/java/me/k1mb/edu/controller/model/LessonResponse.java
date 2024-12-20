package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import me.k1mb.edu.repository.entity.LessonEntity;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * DTO response для {@link LessonEntity}
 */
@Schema(description = "DTO ответа с информацией об уроке")
public record LessonResponse(
    @Schema(description = "Уникальный идентификатор урока",
        requiredMode = REQUIRED)
    UUID id,

    @Schema(description = "Идентификатор курса, к которому относится урок",
        requiredMode = REQUIRED)
    UUID courseId,

    @Schema(description = "Название урока",
        example = "Введение в программирование",
        requiredMode = REQUIRED)
    String title,

    @Schema(description = "Описание урока",
        example = "В этом уроке вы узнаете основы программирования",
        requiredMode = REQUIRED)
    String description,

    @Schema(description = "Продолжительность урока в минутах",
        example = "45",
        requiredMode = RequiredMode.NOT_REQUIRED)
    Integer duration) {
}