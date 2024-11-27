package me.k1mb.edu.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.k1mb.edu.DTO.CourseDtoRequest;
import me.k1mb.edu.DTO.CourseDtoResponse;
import me.k1mb.edu.DTO.CourseMapper;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<CourseDtoResponse> getAll() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).toList();
    }

    public CourseDtoResponse getById(UUID id) {
        return courseRepository
                .findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(id)));
    }

    public CourseDtoResponse createCourse(CourseDtoRequest course) {
        var courseEntity = courseMapper.toEntity(course);
        return courseMapper.toDto(courseRepository.save(courseEntity));
    }

    public CourseDtoResponse updateCourse(UUID id, CourseDtoRequest course) {
        var courseEntity = courseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(id)));
        courseMapper.partialUpdate(course, courseEntity);
        return courseMapper.toDto(courseRepository.save(courseEntity));
    }

    public void deleteCourse(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found %s".formatted(id));
        }
        courseRepository.deleteById(id);
    }

    public UUID checkAuthor(UUID course_id) {
        var course = courseRepository
                .findById(course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(course_id)));
        return course.getAuthor().getId();
    }
}
