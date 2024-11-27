package me.k1mb.edu.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.k1mb.edu.DTO.LessonDtoRequest;
import me.k1mb.edu.DTO.LessonDtoResponse;
import me.k1mb.edu.DTO.LessonMapper;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.repository.LessonRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    public List<LessonDtoResponse> getAllByCourseId(UUID courseId) {
        return lessonRepository.findAllByCourseId(courseId).stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    public LessonDtoResponse createLesson(UUID courseId, LessonDtoRequest lesson) {
        var course = courseRepository
                .findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(courseId)));
        var lessonEntity = lessonMapper.toEntity(lesson);
        lessonEntity.setCourse(course);
        return lessonMapper.toDto(lessonRepository.save(lessonEntity));
    }
}
