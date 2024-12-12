package me.k1mb.edu.mapper;

import me.k1mb.edu.dto.CourseDto;
import me.k1mb.edu.dto.CourseRequest;
import me.k1mb.edu.dto.CourseResponse;
import me.k1mb.edu.model.Course;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    @Mapping(source = "author.id", target = "authorId")
    CourseDto toDto(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "authorId", target = "author.id")
    Course toEntity(CourseDto courseDto);

    @Mapping(target = "authorId", source = "authorId")
    CourseDto toDto(CourseRequest courseRequest);

    @Mapping(source = "authorId", target = "authorId")
    CourseResponse toResponse(CourseDto courseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorId", target = "author.id")
    void partialUpdate(CourseDto courseDto, @MappingTarget Course course);
}