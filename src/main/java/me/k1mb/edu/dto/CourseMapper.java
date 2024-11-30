package me.k1mb.edu.dto;

import lombok.NonNull;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    @Mapping(source = "authorId", target = "author.id")
    Course toEntity(CourseDtoRequest courseDtoRequest);

    @Mapping(source = "author.id", target = "authorId")
    CourseDtoResponse toDto(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorId", target = "author")
    void partialUpdate(CourseDtoRequest courseDtoRequest, @MappingTarget Course course);

    default User createUser(@NonNull UUID authorId) {
        User user = new User();
        user.setId(authorId);
        return user;
    }
}