package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.k1mb.edu.dto.CourseDto;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.mapper.CourseMapper;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class CourseServiceImpl implements CourseService {
    static String COURSE_NOT_FOUND = "Course not found %s";
    CourseRepository courseRepository;
    CourseMapper courseMapper;

    public List<CourseDto> getAll() {
        return courseRepository.findAll().stream()
            .map(courseMapper::toDto).toList();
    }

    public CourseDto getById(@NonNull final UUID id) {
        return courseRepository.findById(id)
            .map(courseMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND.formatted(id)));
    }

    public CourseDto createCourse(@NonNull final CourseDto course) {
        return courseMapper.toDto(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDto updateCourse(@NonNull final UUID id, @NonNull final CourseDto course) {

        var courseEntity = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND.formatted(id)));
        courseMapper.partialUpdate(course, courseEntity);
        return courseMapper.toDto(courseRepository.save(courseEntity));
    }

    public void deleteCourse(@NonNull final UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException(COURSE_NOT_FOUND.formatted(id));
        }
        courseRepository.deleteById(id);
    }

    public UUID checkAuthor(@NonNull final UUID course_id) {
        return courseRepository.findById(course_id)
            .map(Course::getAuthor)
            .map(User::getId)
            .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND.formatted(course_id)));
    }
}
