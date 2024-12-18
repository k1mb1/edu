package me.k1mb.edu.service.mapper;

import me.k1mb.edu.repository.entity.CourseEntity;
import me.k1mb.edu.service.model.CourseDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseEntityMapper {
    @Mapping(source = "author.id", target = "authorId")
    CourseDto toDto(CourseEntity courseEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "authorId", target = "author.id")
    CourseEntity toEntity(CourseDto courseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorId", target = "author.id")
    void partialUpdate(CourseDto courseDto, @MappingTarget CourseEntity courseEntity);
}
