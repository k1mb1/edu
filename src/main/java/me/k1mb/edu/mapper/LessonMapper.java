package me.k1mb.edu.mapper;

import me.k1mb.edu.dto.LessonDto;
import me.k1mb.edu.dto.LessonRequest;
import me.k1mb.edu.dto.LessonResponse;
import me.k1mb.edu.model.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {
    @Mapping(source = "course.id", target = "courseId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "courseId", target = "course.id")
    Lesson toEntity(LessonDto lessonDto);

    @Mapping(source = "courseId", target = "courseId")
    LessonDto toDto(LessonRequest lessonRequest);

    @Mapping(source = "courseId", target = "courseId")
    LessonResponse toResponse(LessonDto lessonDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "courseId", target = "course.id")
    void partialUpdate(LessonRequest lessonRequest, @MappingTarget Lesson lesson);
}
