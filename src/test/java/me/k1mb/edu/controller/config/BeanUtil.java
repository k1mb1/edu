package me.k1mb.edu.controller.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.CourseResponse;
import me.k1mb.edu.controller.model.LessonRequest;
import me.k1mb.edu.controller.model.LessonResponse;
import me.k1mb.edu.repository.entity.CourseEntity;
import me.k1mb.edu.repository.entity.UserEntity;
import me.k1mb.edu.service.model.CourseDto;
import me.k1mb.edu.service.model.LessonDto;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@UtilityClass
public class BeanUtil {
    @Getter
    UUID courseId = randomUUID();
    UUID authorId = UUID.fromString("00a8998c-271d-4f1a-968f-821135c11a88");//TODO менять по сценариям Admin/User
    UUID lessonId = randomUUID();
    String courseNotFoundMessage = "Курс с id=%s не найден";

    public static CourseEntity getCourseEntity() {
        return CourseEntity.builder()
            .id(courseId)
            .title("title")
            .description("description")
            .author(UserEntity.builder().id(authorId).build())
            .build();
    }

    public static CourseDto getCourseDto() {
        return new CourseDto(
            courseId,
            "title",
            "description",
            authorId,
            null,
            null);
    }

    public static CourseResponse getCourseResponse() {
        return new CourseResponse(
            courseId,
            "title",
            "description",
            authorId,
            null,
            null);
    }

    public static CourseRequest getCourseRequest() {
        return new CourseRequest("title", "description", authorId);
    }

    public static LessonRequest getLessonRequest() {
        return new LessonRequest(
            courseId,
            "title",
            "description",
            10);
    }

    public static LessonResponse getLessonResponse() {
        return new LessonResponse(
            lessonId,
            courseId,
            "title",
            "description",
            10);
    }

    public static LessonDto getLessonDto() {
        return new LessonDto(
            lessonId,
            courseId,
            "title",
            "description",
            10);
    }

    public static String getCourseNotFoundMessage(UUID id) {
        return courseNotFoundMessage.formatted(id);
    }

    public static CourseRequest getUpdatedCourseRequest() {
        return new CourseRequest("updated title", "updated description", authorId);
    }
}
