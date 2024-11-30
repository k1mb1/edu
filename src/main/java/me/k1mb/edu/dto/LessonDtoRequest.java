package me.k1mb.edu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * DTO for {@link me.k1mb.edu.model.Lesson}
 */
public record LessonDtoRequest(
    @NotNull UUID courseId,
    @NotBlank String title,
    @NotBlank String description,
    @Positive Integer duration) {
}
