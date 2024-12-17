package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.k1mb.edu.repository.model.Course;

import java.util.UUID;

/**
 * DTO request для {@link Course}
 */
@Schema(description = "DTO запроса для создания или обновления курса")
public record CourseRequest(
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Название курса",
        example = "Основы программирования",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String title,

    @NotBlank
    @Schema(description = "Описание курса",
        example = "Курс по основам программирования на языке Java",
        requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @NotNull
    @Schema(description = "Идентификатор автора курса",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED)
    UUID authorId) {
}