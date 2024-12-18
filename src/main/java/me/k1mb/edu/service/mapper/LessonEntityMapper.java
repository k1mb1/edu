package me.k1mb.edu.service.mapper;

import me.k1mb.edu.repository.entity.LessonEntity;
import me.k1mb.edu.service.model.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonEntityMapper {
    @Mapping(source = "courseEntity.id", target = "courseId")
    LessonDto toDto(LessonEntity lessonEntity);

    @Mapping(source = "courseId", target = "courseEntity.id")
    LessonEntity toEntity(LessonDto lessonDto);
}
