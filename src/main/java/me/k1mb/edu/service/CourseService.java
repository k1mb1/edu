package me.k1mb.edu.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;
import me.k1mb.edu.dto.CourseMapper;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class CourseService {

    CourseRepository courseRepository;
    CourseMapper courseMapper;

    public List<CourseDtoResponse> getAll() {

        return courseRepository.findAll().stream()
            .map(courseMapper::toDto)
            .toList();
    }

    public CourseDtoResponse getById(@NonNull UUID id) {

        return courseRepository.findById(id).
            map(courseMapper::toDto).
            orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(id)));
    }

    public CourseDtoResponse createCourse(@NonNull CourseDtoRequest course) {

        return courseMapper.toDto(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDtoResponse updateCourse(@NonNull UUID id, @NonNull CourseDtoRequest course) {

        var courseEntity = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(id)));
        courseMapper.partialUpdate(course, courseEntity);
        return courseMapper.toDto(courseRepository.save(courseEntity));
    }

    public void deleteCourse(@NonNull UUID id) {

        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found %s".formatted(id));
        }
        courseRepository.deleteById(id);
    }

    public UUID checkAuthor(@NonNull UUID course_id) {
        return courseRepository.findById(course_id).
            map(Course::getAuthor).
            map(User::getId).
            orElseThrow(() -> new ResourceNotFoundException("Course not found %s".formatted(course_id)));
    }
}
