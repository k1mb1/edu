package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.service.model.CourseDto;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseDto> getAll();

    CourseDto getById(@NonNull UUID id);

    CourseDto createCourse(@NonNull CourseDto course);

    CourseDto updateCourse(@NonNull UUID id, @NonNull CourseDto course);

    void deleteCourse(@NonNull UUID id);

    UUID checkAuthor(@NonNull UUID courseId);
}
