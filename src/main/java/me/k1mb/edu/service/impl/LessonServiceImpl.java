package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.dto.LessonRequest;
import me.k1mb.edu.dto.LessonResponse;
import me.k1mb.edu.mapper.LessonMapper;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.repository.LessonRepository;
import me.k1mb.edu.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    CourseRepository courseRepository;
    LessonMapper lessonMapper;

    public List<LessonResponse> getAllByCourseId(@NonNull final UUID courseId) {
        return lessonRepository.findAllByCourseId(courseId).stream()
            .map(lessonMapper::toDto)
            .toList();
    }

    public LessonResponse createLesson(@NonNull final UUID courseId, @NonNull final LessonRequest lesson) {

        val course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(courseId)));
        val lessonEntity = lessonMapper.toEntity(lesson);
        return lessonMapper.toDto(
            lessonRepository.save(
                lessonEntity.setCourse(course)));
    }
}
