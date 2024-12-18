package me.k1mb.edu.service.model;

import me.k1mb.edu.repository.entity.CourseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link CourseEntity}
 */
public record CourseDto(
    UUID id,
    String title,
    String description,
    UUID authorId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}