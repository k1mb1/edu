package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseDtoResponse> getAll();

    CourseDtoResponse getById(@NonNull UUID id);

    CourseDtoResponse createCourse(@NonNull CourseDtoRequest course);

    CourseDtoResponse updateCourse(@NonNull UUID id, @NonNull CourseDtoRequest course);

    void deleteCourse(@NonNull UUID id);

    UUID checkAuthor(@NonNull UUID course_id);
}
