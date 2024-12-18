package me.k1mb.edu.service.model;

import me.k1mb.edu.repository.entity.LessonEntity;

import java.util.UUID;

/**
 * DTO для {@link LessonEntity}
 */
public record LessonDto(
    UUID id,
    UUID courseId,
    String title,
    String description,
    Integer duration) {
}