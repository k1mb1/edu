package me.k1mb.edu.service.mapper;

import me.k1mb.edu.repository.model.Lesson;
import me.k1mb.edu.service.model.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonEntityMapper {
    @Mapping(source = "course.id", target = "courseId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "courseId", target = "course.id")
    Lesson toEntity(LessonDto lessonDto);
}
