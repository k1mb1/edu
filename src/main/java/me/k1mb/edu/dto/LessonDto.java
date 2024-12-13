package me.k1mb.edu.dto;

import java.util.UUID;

/**
 * DTO для {@link me.k1mb.edu.model.Lesson}
 */
public record LessonDto(
    UUID id,
    UUID courseId,
    String title,
    String description,
    Integer duration) {
}