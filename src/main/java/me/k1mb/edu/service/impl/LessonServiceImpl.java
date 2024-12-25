package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.repository.LessonRepository;
import me.k1mb.edu.service.LessonService;
import me.k1mb.edu.service.exception.ResourceNotFoundException;
import me.k1mb.edu.service.mapper.LessonEntityMapper;
import me.k1mb.edu.service.model.LessonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class LessonServiceImpl implements LessonService {
    static String LESSON_NOT_FOUND = "Урок с id=%s не найден";
    LessonRepository lessonRepository;
    CourseRepository courseRepository;
    LessonEntityMapper lessonMapper;

    public List<LessonDto> getAllByCourseId(@NonNull final UUID courseId) {
        return lessonRepository.findAllByCourseEntityId(courseId).stream()
            .map(lessonMapper::toDto)
            .toList();
    }

    public LessonDto createLesson(@NonNull final UUID courseId, final @NonNull LessonDto lessonDto) {
        val course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND.formatted(courseId)));
        val lessonEntity = lessonMapper.toEntity(lessonDto).setCourseEntity(course);
        return lessonMapper.toDto(
            lessonRepository.save(lessonEntity));
    }

    public void deleteLesson(@NonNull UUID courseId, @NonNull UUID lessonId) {
        val lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND.formatted(lessonId)));
        if (!lesson.getCourseEntity().getId().equals(courseId)) {
            throw new IllegalArgumentException("Урок не принадлежит курсу");
        }

        lessonRepository.delete(lesson);
    }
}
