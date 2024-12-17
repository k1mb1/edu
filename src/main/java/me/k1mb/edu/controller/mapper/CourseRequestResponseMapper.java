package me.k1mb.edu.controller.mapper;

import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.CourseResponse;
import me.k1mb.edu.service.model.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseRequestResponseMapper {
    @Mapping(target = "authorId", source = "authorId")
    CourseDto toDto(CourseRequest courseRequest);

    @Mapping(source = "authorId", target = "authorId")
    CourseResponse toResponse(CourseDto courseDto);
}
