package me.k1mb.edu.controller.mapper;

import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.CourseResponse;
import me.k1mb.edu.service.model.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseRequestResponseMapper {
    CourseDto toDto(CourseRequest courseRequest);

    CourseResponse toResponse(CourseDto courseDto);
}
