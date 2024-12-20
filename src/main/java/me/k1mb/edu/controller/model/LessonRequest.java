package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.k1mb.edu.repository.entity.LessonEntity;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * DTO request для {@link LessonEntity}
 */
@Schema(description = "DTO запроса для создания или обновления урока")
public record LessonRequest(
    @NotNull
    @Schema(description = "Идентификатор курса, к которому относится урок",
        requiredMode = REQUIRED)
    UUID courseId,

    @NotBlank
    @Schema(description = "Название урока",
        example = "Введение в программирование",
        requiredMode = REQUIRED)
    String title,

    @NotBlank
    @Schema(description = "Описание урока",
        example = "В этом уроке вы узнаете основы программирования",
        requiredMode = REQUIRED)
    String description,

    @Positive
    @Schema(description = "Продолжительность урока в минутах",
        example = "45",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Integer duration) {
}