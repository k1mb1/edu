package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseDtoResponse> getAll();

    CourseDtoResponse getById(@NonNull final UUID id);

    CourseDtoResponse createCourse(@NonNull final CourseDtoRequest course);

    CourseDtoResponse updateCourse(@NonNull final UUID id, @NonNull final CourseDtoRequest course);

    void deleteCourse(@NonNull final UUID id);

    UUID checkAuthor(@NonNull final UUID course_id);
}
