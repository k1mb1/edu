package me.k1mb.edu.dto;

import lombok.NonNull;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.Lesson;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {
    @Mapping(source = "courseId", target = "course.id")
    Lesson toEntity(LessonDtoRequest lessonDtoRequest);

    @Mapping(source = "course.id", target = "courseId")
    LessonDtoResponse toDto(Lesson lesson);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "courseId", target = "course.id")
    void partialUpdate(LessonDtoRequest lessonDtoRequest, @MappingTarget Lesson lesson);

    default Course createCourse(@NonNull UUID courseId) {
        return new Course().setId(courseId);
    }
}
