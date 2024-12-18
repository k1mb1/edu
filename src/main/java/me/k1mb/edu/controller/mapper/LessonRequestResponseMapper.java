package me.k1mb.edu.controller.mapper;

import me.k1mb.edu.controller.model.LessonRequest;
import me.k1mb.edu.controller.model.LessonResponse;
import me.k1mb.edu.service.model.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonRequestResponseMapper {
    LessonDto toDto(LessonRequest lessonRequest);

    LessonResponse toResponse(LessonDto lessonDto);
}
