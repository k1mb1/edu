package me.k1mb.edu.service.model;

import me.k1mb.edu.repository.model.Lesson;

import java.util.UUID;

/**
 * DTO для {@link Lesson}
 */
public record LessonDto(
    UUID id,
    UUID courseId,
    String title,
    String description,
    Integer duration) {
}