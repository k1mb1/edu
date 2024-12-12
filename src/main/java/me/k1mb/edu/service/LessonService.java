package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.dto.LessonRequest;
import me.k1mb.edu.dto.LessonResponse;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    List<LessonResponse> getAllByCourseId(@NonNull UUID courseId);

    LessonResponse createLesson(@NonNull UUID courseId, @NonNull LessonRequest lesson);
}
