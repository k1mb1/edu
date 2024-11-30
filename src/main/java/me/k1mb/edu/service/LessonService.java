package me.k1mb.edu.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.dto.LessonDtoRequest;
import me.k1mb.edu.dto.LessonDtoResponse;
import me.k1mb.edu.dto.LessonMapper;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class LessonService {
    LessonRepository lessonRepository;
    CourseRepository courseRepository;
    LessonMapper lessonMapper;

    public List<LessonDtoResponse> getAllByCourseId(@NonNull UUID courseId) {
        return lessonRepository.findAllByCourseId(courseId).stream()
            .map(lessonMapper::toDto)
            .toList();
    }

    public LessonDtoResponse createLesson(@NonNull UUID courseId, @NonNull LessonDtoRequest lesson) {
        val course = courseRepository
            .findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(courseId)));
        val lessonEntity = lessonMapper.toEntity(lesson);
        lessonEntity.setCourse(course);
        return lessonMapper.toDto(lessonRepository.save(lessonEntity));
    }
}
