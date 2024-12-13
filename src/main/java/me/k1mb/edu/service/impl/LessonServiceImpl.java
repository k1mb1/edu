package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.dto.LessonDto;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.mapper.LessonMapper;
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

    public List<LessonDto> getAllByCourseId(@NonNull final UUID courseId) {
        return lessonRepository.findAllByCourseId(courseId).stream()
            .map(lessonMapper::toDto)
            .toList();
    }

    public LessonDto createLesson(@NonNull final UUID courseId, final @NonNull LessonDto lessonDto) {

        val course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(courseId)));
        val lessonEntity = lessonMapper.toEntity(lessonDto);
        return lessonMapper.toDto(
            lessonRepository.save(
                lessonEntity.setCourse(course)));
    }
}
