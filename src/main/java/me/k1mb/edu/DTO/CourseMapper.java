package me.k1mb.edu.DTO;

import java.util.UUID;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    @Mapping(source = "authorId", target = "author.id")
    Course toEntity(CourseDtoRequest courseDtoRequest);

    @Mapping(source = "author.id", target = "authorId")
    CourseDtoResponse toDto(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "authorId", target = "author")
    Course partialUpdate(CourseDtoRequest courseDtoRequest, @MappingTarget Course course);

    default User createUser(UUID authorId) {
        if (authorId == null) {
            return null;
        }
        User user = new User();
        user.setId(authorId);
        return user;
    }
}
