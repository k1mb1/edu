package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.dto.LessonDtoRequest;
import me.k1mb.edu.dto.LessonDtoResponse;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    List<LessonDtoResponse> getAllByCourseId(@NonNull UUID courseId);

    LessonDtoResponse createLesson(@NonNull UUID courseId, @NonNull LessonDtoRequest lesson);
}
