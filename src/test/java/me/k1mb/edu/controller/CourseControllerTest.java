package me.k1mb.edu.controller;

import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;
import me.k1mb.edu.dto.LessonDtoRequest;
import me.k1mb.edu.dto.LessonDtoResponse;
import me.k1mb.edu.exeption.ResourceNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Test class for the {@link CourseController}
 */
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    final UUID courseId = UUID.randomUUID();
    final CourseDtoRequest courseDtoRequest = new CourseDtoRequest("title1", "description1", courseId);
    final CourseDtoResponse courseDtoResponse = new CourseDtoResponse(
        UUID.randomUUID(),
        "title1",
        "description1",
        courseId,
        null,
        null);
    final LessonDtoRequest lessonDtoRequest = new LessonDtoRequest(courseId, "title1", "description1", 10);
    final LessonDtoResponse lessonDtoResponse = new LessonDtoResponse(
        UUID.randomUUID(),
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
    public void getAll() {
        var expectedCourses = List.of(courseDtoResponse);
        doReturn(expectedCourses).when(courseService).getAll();

        ResponseEntity<List<CourseDtoResponse>> response = courseController.getAll();

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(expectedCourses, OK);
        verify(courseService).getAll();
    }

    @Test
    public void createCourse() {
        doReturn(courseDtoResponse).when(courseService).createCourse(courseDtoRequest);

        var response = courseController.createCourse(courseDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, CREATED);
        verify(courseService).createCourse(courseDtoRequest);
    }

    @Test
    public void getById() {
        doReturn(courseDtoResponse).when(courseService).getById(courseId);

        var response = courseController.getById(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, OK);
        verify(courseService).getById(courseId);
    }

    @Test
    public void getByIdException() {
        doThrow(new ResourceNotFoundException("Course not found %s".formatted(courseId))).when(courseService).getById(
            courseId);

        assertThrows(ResourceNotFoundException.class, () -> courseController.getById(courseId));
        verify(courseService).getById(courseId);
    }

    @Test
    public void deleteCourse() {
        doNothing().when(courseService).deleteCourse(courseId);

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(null, NO_CONTENT);
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    public void deleteCourseException() {
        doThrow(new ResourceNotFoundException("Course not found %s".formatted(courseId))).when(courseService).deleteCourse(
            courseId);

        assertThrows(ResourceNotFoundException.class, () -> courseController.deleteCourse(courseId));
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    public void updateCourse() {
        doReturn(courseDtoResponse).when(courseService).updateCourse(courseId, courseDtoRequest);

        var response = courseController.updateCourse(courseId, courseDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, OK);
        verify(courseService).updateCourse(courseId, courseDtoRequest);
    }

    @Test
    public void updateCourseException() {
        doThrow(new ResourceNotFoundException("Course not found %s".formatted(courseId))).when(courseService).updateCourse(
            courseId,
            courseDtoRequest);

        assertThrows(ResourceNotFoundException.class, () -> courseController.deleteCourse(courseId));
        verify(courseService).updateCourse(courseId, courseDtoRequest);
    }

    @Test
    public void getAllLessonsByCourseId() {
        var list = List.of(lessonDtoResponse);
        doReturn(list).when(lessonService).getAllByCourseId(courseId);

        ResponseEntity<List<LessonDtoResponse>> response = courseController.getAllLessonsByCourseId(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(list, OK);
        verify(lessonService).getAllByCourseId(courseId);
    }

    @Test
    public void createLesson() {
        doReturn(lessonDtoResponse).when(lessonService).createLesson(courseId, lessonDtoRequest);

        ResponseEntity<LessonDtoResponse> response = courseController.createLesson(courseId, lessonDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(lessonDtoResponse, CREATED);
        verify(lessonService).createLesson(courseId, lessonDtoRequest);
    }
}
