package me.k1mb.edu.controller;

import lombok.val;
import me.k1mb.edu.dto.*;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.mapper.CourseMapper;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
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

    @Mock
    CourseService courseService;
    @Mock
    CourseMapper courseMapper;
    @Mock
    LessonService lessonService;
    @InjectMocks
    CourseController courseController;

    @Test
    void getAll() {
        val expectedCourses = List.of(courseDto);
        doReturn(expectedCourses).when(courseService).getAll();
        doReturn(courseResponse).when(courseMapper).toResponse(courseDto);

        ResponseEntity<List<CourseResponse>> response = courseController.getAll();

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(List.of(courseResponse), OK);
        verify(courseService).getAll();
        verify(courseMapper).toResponse(courseDto);
    }

    @Test
    void createCourse() {
        doReturn(courseDto).when(courseMapper).toDto(courseRequest);
        doReturn(courseDto).when(courseService).createCourse(courseDto);
        doReturn(courseResponse).when(courseMapper).toResponse(courseDto);

        val response = courseController.createCourse(courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, CREATED);
        verify(courseMapper).toDto(courseRequest);
        verify(courseService).createCourse(courseDto);
        verify(courseMapper).toResponse(courseDto);
    }

    @Test
    void getById() {
        doReturn(courseDto).when(courseService).getById(courseId);
        doReturn(courseResponse).when(courseMapper).toResponse(courseDto);

        val response = courseController.getById(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).getById(courseId);
        verify(courseMapper).toResponse(courseDto);
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
        doReturn(courseResponse).when(courseMapper).toResponse(courseDto);
        doReturn(courseDto).when(courseMapper).toDto(courseRequest);
        doReturn(courseDto).when(courseService).updateCourse(courseId, courseDto);

        val response = courseController.updateCourse(courseId, courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).updateCourse(courseId, courseMapper.toDto(courseRequest));
        verify(courseMapper).toResponse(courseDto);
        verify(courseMapper, times(2)).toDto(courseRequest);
    }

    @Test
    void updateCourseException() {
        val message = "Course not found %s".formatted(courseId);
        doThrow(new ResourceNotFoundException(message)).when(courseService)
            .updateCourse(courseId, courseDto);
        doReturn(courseDto).when(courseMapper).toDto(courseRequest);

        assertThatThrownBy(() -> courseController.updateCourse(courseId, courseRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);
        verify(courseService).updateCourse(courseId, courseDto);
        verify(courseMapper).toDto(courseRequest);
    }

    @Test
    void getAllLessonsByCourseId() {
        val list = List.of(lessonResponse);
        doReturn(list).when(lessonService).getAllByCourseId(courseId);

        val response = courseController.getAllLessonsByCourseId(courseId);

        assertThat(response).isNotNull().extracting(
            ResponseEntity::getBody,
            ResponseEntity::getStatusCode).containsExactly(list, OK);
        verify(lessonService).getAllByCourseId(courseId);
    }

    @Test
    void createLesson() {
        doReturn(lessonResponse).when(lessonService).createLesson(courseId, lessonRequest);

        val response = courseController.createLesson(courseId, lessonRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(lessonResponse, CREATED);
        verify(lessonService).createLesson(courseId, lessonRequest);
    }
}
