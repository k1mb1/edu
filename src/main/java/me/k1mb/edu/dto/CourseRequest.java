package me.k1mb.edu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO request для {@link me.k1mb.edu.model.Course}
 */
public record CourseRequest(
    @NotBlank @Size(max = 255) String title,
    @NotBlank String description,
    @NotNull UUID authorId) {
}
