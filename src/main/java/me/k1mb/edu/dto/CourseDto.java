package me.k1mb.edu.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link me.k1mb.edu.model.Course}
 */
public record CourseDto(
    UUID id,
    String title,
    String description,
    UUID authorId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}