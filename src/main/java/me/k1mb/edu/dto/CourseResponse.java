package me.k1mb.edu.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO response для {@link me.k1mb.edu.model.Course}
 */
public record CourseResponse(
    UUID id,
    String title,
    String description,
    UUID authorId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
