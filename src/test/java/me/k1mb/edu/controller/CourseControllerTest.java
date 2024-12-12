package me.k1mb.edu.controller;

import lombok.val;
import me.k1mb.edu.dto.CourseRequest;
import me.k1mb.edu.dto.CourseResponse;
import me.k1mb.edu.dto.LessonRequest;
import me.k1mb.edu.dto.LessonResponse;
import me.k1mb.edu.exception.ResourceNotFoundException;
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
    final CourseRequest courseRequest = new CourseRequest("title1", "description1", courseId);
    final CourseResponse courseResponse = new CourseResponse(
        randomUUID(),
        "title1",
        "description1",
        courseId,
        null,
        null);
    final LessonRequest lessonRequest = new LessonRequest(courseId, "title1", "description1", 10);
    final LessonResponse lessonResponse = new LessonResponse(
        randomUUID(),
        courseId,
        "title1",
        "description1",
        10);
    @Mock
    CourseService courseService;
    @Mock
    LessonService lessonService;
    @InjectMocks
    CourseController courseController;

    @Test
    void getAll() {
        val expectedCourses = List.of(courseResponse);
        doReturn(expectedCourses).when(courseService).getAll();

        ResponseEntity<List<CourseResponse>> response = courseController.getAll();

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(expectedCourses, OK);
        verify(courseService).getAll();
    }

    @Test
    void createCourse() {
        doReturn(courseResponse).when(courseService).createCourse(courseRequest);

        val response = courseController.createCourse(courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, CREATED);
        verify(courseService).createCourse(courseRequest);
    }

    @Test
    void getById() {
        doReturn(courseResponse).when(courseService).getById(courseId);

        val response = courseController.getById(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).getById(courseId);
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
        doReturn(courseResponse).when(courseService).updateCourse(courseId, courseRequest);

        val response = courseController.updateCourse(courseId, courseRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);
        verify(courseService).updateCourse(courseId, courseRequest);
    }

    @Test
    void updateCourseException() {
        val message = "Course not found %s".formatted(courseId);
        doThrow(new ResourceNotFoundException(message)).when(courseService)
            .updateCourse(courseId, courseRequest);

        assertThatThrownBy(() -> courseController.updateCourse(courseId, courseRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);
        verify(courseService).updateCourse(courseId, courseRequest);
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
