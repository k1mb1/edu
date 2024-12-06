package me.k1mb.edu.controller;

import lombok.val;
import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;
import me.k1mb.edu.dto.LessonDtoRequest;
import me.k1mb.edu.dto.LessonDtoResponse;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

/**
 * Test class for the {@link CourseController}
 */
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    CourseService courseService;

    @Mock
    LessonService lessonService;

    @InjectMocks
    CourseController courseController;

    CourseDtoRequest courseDtoRequest;
    CourseDtoResponse courseDtoResponse;
    LessonDtoRequest lessonDtoRequest;
    LessonDtoResponse lessonDtoResponse;
    UUID courseId;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        courseDtoRequest = new CourseDtoRequest("title1", "description1", courseId);
        courseDtoResponse = new CourseDtoResponse(UUID.randomUUID(), "title1", "description1", courseId, null, null); // Initialize with test data
        lessonDtoRequest = new LessonDtoRequest(courseId,"title1", "description1", 10);
        lessonDtoResponse = new LessonDtoResponse(UUID.randomUUID(), courseId,"title1", "description1", 10);
    }

    @Test
    public void getAll() {
        List<CourseDtoResponse> expectedCourses = List.of(courseDtoResponse);
        when(courseService.getAll()).thenReturn(expectedCourses);

        ResponseEntity<List<CourseDtoResponse>> response = courseController.getAll();

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(expectedCourses, OK);
        verify(courseService).getAll();
    }

    @Test
    public void createCourse() {
        when(courseService.createCourse(courseDtoRequest)).thenReturn(courseDtoResponse);

        val response = courseController.createCourse(courseDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, CREATED);
        verify(courseService).createCourse(courseDtoRequest);
    }

    @Test
    public void getById() {
        when(courseService.getById(courseId)).thenReturn(courseDtoResponse);

        val response = courseController.getById(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, OK);
        verify(courseService).getById(courseId);
    }

    @Test
    public void deleteCourse() {

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody,ResponseEntity::getStatusCode)
            .containsExactly(null, NO_CONTENT);
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    public void updateCourse() {
        when(courseService.updateCourse(courseId, courseDtoRequest)).thenReturn(courseDtoResponse);

        val response = courseController.updateCourse(courseId, courseDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseDtoResponse, OK);
        verify(courseService).updateCourse(courseId, courseDtoRequest);
    }

    @Test
    public void getAllLessonsByCourseId() {
        when(lessonService.getAllByCourseId(courseId)).thenReturn(List.of());

        ResponseEntity<List<LessonDtoResponse>> response = courseController.getAllLessonsByCourseId(courseId);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(List.of(), OK);
        verify(lessonService).getAllByCourseId(courseId);
    }

    @Test
    public void createLesson() {
        when(lessonService.createLesson(courseId, lessonDtoRequest)).thenReturn(lessonDtoResponse);

        ResponseEntity<LessonDtoResponse> response = courseController.createLesson(courseId, lessonDtoRequest);

        assertThat(response)
            .isNotNull()
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(lessonDtoResponse, CREATED);
        verify(lessonService).createLesson(courseId, lessonDtoRequest);
    }
}
