package me.k1mb.edu.dto;

import java.util.UUID;

/**
 * DTO for {@link me.k1mb.edu.model.Lesson}
 */
public record LessonDtoResponse(
    UUID id,
    UUID courseId,
    String title,
    String description,
    Integer duration) {
}
