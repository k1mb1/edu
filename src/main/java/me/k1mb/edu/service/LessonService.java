package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.service.model.LessonDto;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    List<LessonDto> getAllByCourseId(@NonNull UUID courseId);

    LessonDto createLesson(@NonNull UUID courseId, @NonNull LessonDto lessonDto);

    void deleteLesson(@NonNull UUID courseId,@NonNull UUID lessonId);
}
