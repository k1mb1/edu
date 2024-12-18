package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import me.k1mb.edu.repository.entity.LessonEntity;

import java.util.UUID;

/**
 * DTO response для {@link LessonEntity}
 */
@Schema(description = "DTO ответа с информацией об уроке")
public record LessonResponse(
    @Schema(description = "Уникальный идентификатор урока",
        requiredMode = RequiredMode.REQUIRED)
    UUID id,

    @Schema(description = "Идентификатор курса, к которому относится урок",
        requiredMode = RequiredMode.REQUIRED)
    UUID courseId,

    @Schema(description = "Название урока",
        example = "Введение в программирование",
        requiredMode = RequiredMode.REQUIRED)
    String title,

    @Schema(description = "Описание урока",
        example = "В этом уроке вы узнаете основы программирования",
        requiredMode = RequiredMode.REQUIRED)
    String description,

    @Schema(description = "Продолжительность урока в минутах",
        example = "45",
        requiredMode = RequiredMode.NOT_REQUIRED)
    Integer duration) {
}