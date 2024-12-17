package me.k1mb.edu.service.mapper;

import me.k1mb.edu.repository.model.Course;
import me.k1mb.edu.service.model.CourseDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseEntityMapper {
    @Mapping(source = "author.id", target = "authorId")
    CourseDto toDto(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "authorId", target = "author.id")
    Course toEntity(CourseDto courseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorId", target = "author.id")
    void partialUpdate(CourseDto courseDto, @MappingTarget Course course);
}
