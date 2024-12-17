package me.k1mb.edu.controller;

import lombok.val;
import me.k1mb.edu.controller.mapper.CourseRequestResponseMapper;
import me.k1mb.edu.controller.mapper.LessonRequestResponseMapper;
import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.CourseResponse;
import me.k1mb.edu.controller.model.LessonRequest;
import me.k1mb.edu.controller.model.LessonResponse;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.repository.model.Course;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import me.k1mb.edu.service.model.CourseDto;
import me.k1mb.edu.service.model.LessonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Тестовый класс для {@link CourseController}
 */
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    final UUID courseId = randomUUID();
    final Course course = new Course().setId(randomUUID());
    final CourseResponse courseResponse = new CourseResponse(
        randomUUID(),
        "title",
        "description",
        courseId,
        null,
        null);
    final CourseRequest courseRequest = new CourseRequest("title", "description", courseId);
    final CourseDto courseDto = new CourseDto(
        randomUUID(),
        "title",
        "description",
        courseId,
        null,
        null);
    final LessonRequest lessonRequest = new LessonRequest(
        course.getId(),
        "title1",
        "description1",
        10);
    final LessonResponse lessonResponse = new LessonResponse(
        randomUUID(),
        course.getId(),
        "title1",
        "description1",
        10);
    final LessonDto lessonDto = new LessonDto(
        randomUUID(),
        course.getId(),
        "title1",
        "description1",
        10
    );

    @Mock
    CourseService courseService;
    @Mock
    CourseRequestResponseMapper mapper;
    @Mock
    LessonRequestResponseMapper lessonMapper;
    @Mock
    LessonService lessonService;
    @InjectMocks
    CourseController courseController;

    @Test
    void getAll() {
        val expectedCourses = List.of(courseDto);
        doReturn(expectedCourses).when(courseService).getAll();
        doReturn(courseResponse).when(mapper).toResponse(courseDto);

        ResponseEntity<List<CourseResponse>> response = courseController.getAll();

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(List.of(courseResponse), OK);
        verify(courseService).getAll();
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void createCourse() {
        doReturn(courseDto).when(mapper).toDto(courseRequest);
        doReturn(courseDto).when(courseService).createCourse(courseDto);
        doReturn(courseResponse).when(mapper).toResponse(courseDto);

        val response = courseController.createCourse(courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, CREATED);
        verify(mapper).toDto(courseRequest);
        verify(courseService).createCourse(courseDto);
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void getById() {
        doReturn(courseDto).when(courseService).getById(courseId);
        doReturn(courseResponse).when(mapper).toResponse(courseDto);

        val response = courseController.getById(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).getById(courseId);
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void getByIdException() {
        val message = "Course not found %s".formatted(courseId);
        doThrow(new ResourceNotFoundException(message))
            .when(courseService).getById(courseId);

        assertThatThrownBy(() -> courseController.getById(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);
        verify(courseService).getById(courseId);
    }

    @Test
    void deleteCourse() {
        doNothing().when(courseService).deleteCourse(courseId);

        val response = courseController.deleteCourse(courseId);

        assertThat(response).isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(null, NO_CONTENT);
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    void deleteCourseException() {
        val message = "Course not found %s".formatted(courseId);
        doThrow(new ResourceNotFoundException(message))
            .when(courseService).deleteCourse(courseId);

        assertThatThrownBy(() -> courseController.deleteCourse(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    void updateCourse() {
        doReturn(courseResponse).when(mapper).toResponse(courseDto);
        doReturn(courseDto).when(mapper).toDto(courseRequest);
        doReturn(courseDto).when(courseService).updateCourse(courseId, courseDto);

        val response = courseController.updateCourse(courseId, courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).updateCourse(courseId, mapper.toDto(courseRequest));
        verify(mapper).toResponse(courseDto);
        verify(mapper, times(2)).toDto(courseRequest);
    }

    @Test
    void updateCourseException() {
        val message = "Course not found %s".formatted(courseId);
        doThrow(new ResourceNotFoundException(message)).when(courseService)
            .updateCourse(courseId, courseDto);
        doReturn(courseDto).when(mapper).toDto(courseRequest);

        assertThatThrownBy(() -> courseController.updateCourse(courseId, courseRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);
        verify(courseService).updateCourse(courseId, courseDto);
        verify(mapper).toDto(courseRequest);
    }

    @Test
    void getAllLessonsByCourseId() {
        val list = List.of(lessonDto);
        doReturn(list).when(lessonService).getAllByCourseId(courseId);
        doReturn(lessonResponse).when(lessonMapper).toResponse(lessonDto);

        val response = courseController.getAllLessonsByCourseId(courseId);

        assertThat(response).isNotNull().extracting(
            ResponseEntity::getBody,
            ResponseEntity::getStatusCode).containsExactly(List.of(lessonResponse), OK);
        verify(lessonService).getAllByCourseId(courseId);
        verify(lessonMapper).toResponse(lessonDto);
    }

    @Test
    void createLesson() {
        doReturn(lessonDto).when(lessonService).createLesson(courseId, lessonDto);
        doReturn(lessonResponse).when(lessonMapper).toResponse(lessonDto);
        doReturn(lessonDto).when(lessonMapper).toDto(lessonRequest);

        val response = courseController.createLesson(courseId, lessonRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(lessonResponse, CREATED);
        verify(lessonService).createLesson(courseId, lessonDto);
        verify(lessonMapper).toResponse(lessonDto);
        verify(lessonMapper).toDto(lessonRequest);
    }
}
