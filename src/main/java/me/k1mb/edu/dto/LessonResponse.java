package me.k1mb.edu.dto;

import java.util.UUID;

/**
 * DTO response для {@link me.k1mb.edu.model.Lesson}
 */
public record LessonResponse(
    UUID id,
    UUID courseId,
    String title,
    String description,
    Integer duration) {
}
