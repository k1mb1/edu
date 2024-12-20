package me.k1mb.edu.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.k1mb.edu.repository.entity.CourseEntity;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * DTO request для {@link CourseEntity}
 */
@Schema(description = "DTO запроса для создания или обновления курса")
public record CourseRequest(
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Название курса",
        example = "Основы программирования",
        requiredMode = REQUIRED)
    String title,

    @NotBlank
    @Schema(description = "Описание курса",
        example = "Курс по основам программирования на языке Java",
        requiredMode = REQUIRED)
    String description,

    @NotNull
    @Schema(description = "Идентификатор автора курса",
        requiredMode = REQUIRED)
    UUID authorId) {
}