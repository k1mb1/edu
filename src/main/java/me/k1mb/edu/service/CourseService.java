package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.dto.CourseRequest;
import me.k1mb.edu.dto.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<CourseResponse> getAll();

    CourseResponse getById(@NonNull UUID id);

    CourseResponse createCourse(@NonNull CourseRequest course);

    CourseResponse updateCourse(@NonNull UUID id, @NonNull CourseRequest course);

    void deleteCourse(@NonNull UUID id);

    UUID checkAuthor(@NonNull UUID course_id);
}
