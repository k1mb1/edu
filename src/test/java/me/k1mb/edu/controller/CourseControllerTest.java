package me.k1mb.edu.controller;

import lombok.val;
import me.k1mb.edu.controller.mapper.CourseRequestResponseMapper;
import me.k1mb.edu.controller.mapper.LessonRequestResponseMapper;
import me.k1mb.edu.controller.model.CourseRequest;
import me.k1mb.edu.controller.model.CourseResponse;
import me.k1mb.edu.controller.model.LessonRequest;
import me.k1mb.edu.controller.model.LessonResponse;
import me.k1mb.edu.repository.entity.CourseEntity;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import me.k1mb.edu.service.exception.ResourceNotFoundException;
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

    static String COURSE_NOT_FOUND = "Курс с id=%s не найден";
    final UUID courseId = randomUUID();
    final CourseEntity courseEntity = new CourseEntity().setId(randomUUID());
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
        courseEntity.getId(),
        "title1",
        "description1",
        10);
    final LessonResponse lessonResponse = new LessonResponse(
        randomUUID(),
        courseEntity.getId(),
        "title1",
        "description1",
        10);
    final LessonDto lessonDto = new LessonDto(
        randomUUID(),
        courseEntity.getId(),
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
        when(courseService.getAll())
            .thenReturn(List.of(courseDto));
        when(mapper.toResponse(courseDto))
            .thenReturn(courseResponse);

        assertThat(courseController.getAll())
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(List.of(courseResponse), OK);

        verify(courseService).getAll();
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void createCourse() {
        when(mapper.toDto(courseRequest))
            .thenReturn(courseDto);
        when(courseService.createCourse(courseDto))
            .thenReturn(courseDto);
        when(mapper.toResponse(courseDto))
            .thenReturn(courseResponse);

        assertThat(courseController.createCourse(courseRequest))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, CREATED);

        verify(mapper).toDto(courseRequest);
        verify(courseService).createCourse(courseDto);
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void getById() {
        when(courseService.getById(courseId))
            .thenReturn(courseDto);
        when(mapper.toResponse(courseDto))
            .thenReturn(courseResponse);

        assertThat(courseController.getById(courseId))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);

        verify(courseService).getById(courseId);
        verify(mapper).toResponse(courseDto);
    }

    @Test
    void getByIdException() {
        val message = COURSE_NOT_FOUND.formatted(courseId);
        when(courseService.getById(courseId))
            .thenThrow(new ResourceNotFoundException(message));

        assertThatThrownBy(() -> courseController.getById(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);

        verify(courseService).getById(courseId);
    }

    @Test
    void deleteCourse() {
        doNothing().when(courseService)
            .deleteCourse(courseId);

        assertThat(courseController.deleteCourse(courseId))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(null, NO_CONTENT);

        verify(courseService).deleteCourse(courseId);
    }

    @Test
    void deleteCourseException() {
        val message = COURSE_NOT_FOUND.formatted(courseId);
        doThrow(new ResourceNotFoundException(message))
            .when(courseService).deleteCourse(courseId);

        assertThatThrownBy(() -> courseController.deleteCourse(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);

        verify(courseService).deleteCourse(courseId);
    }

    @Test
    void updateCourse() {
        when(mapper.toResponse(courseDto))
            .thenReturn(courseResponse);
        when(mapper.toDto(courseRequest))
            .thenReturn(courseDto);
        when(courseService.updateCourse(courseId, courseDto))
            .thenReturn(courseDto);

        assertThat(courseController.updateCourse(courseId, courseRequest))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(courseResponse, OK);

        verify(courseService).updateCourse(courseId, mapper.toDto(courseRequest));
        verify(mapper).toResponse(courseDto);
        verify(mapper, times(2)).toDto(courseRequest);
    }

    @Test
    void updateCourseException() {
        val message = COURSE_NOT_FOUND.formatted(courseId);
        when(courseService.updateCourse(courseId, courseDto)).
            thenThrow(new ResourceNotFoundException(message));
        when(mapper.toDto(courseRequest))
            .thenReturn(courseDto);

        assertThatThrownBy(() -> courseController.updateCourse(courseId, courseRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message);

        verify(courseService).updateCourse(courseId, courseDto);
        verify(mapper).toDto(courseRequest);
    }

    @Test
    void getAllLessonsByCourseId() {
        when(lessonService.getAllByCourseId(courseId))
            .thenReturn(List.of(lessonDto));
        when(lessonMapper.toResponse(lessonDto))
            .thenReturn(lessonResponse);

        assertThat(courseController.getAllLessonsByCourseId(courseId))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(List.of(lessonResponse), OK);

        verify(lessonService).getAllByCourseId(courseId);
        verify(lessonMapper).toResponse(lessonDto);
    }

    @Test
    void createLesson() {
        when(lessonService.createLesson(courseId, lessonDto))
            .thenReturn(lessonDto);
        when(lessonMapper.toResponse(lessonDto))
            .thenReturn(lessonResponse);
        when(lessonMapper.toDto(lessonRequest))
            .thenReturn(lessonDto);

        assertThat(courseController.createLesson(courseId, lessonRequest))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(lessonResponse, CREATED);

        verify(lessonService).createLesson(courseId, lessonDto);
        verify(lessonMapper).toResponse(lessonDto);
        verify(lessonMapper).toDto(lessonRequest);
    }

    @Test
    void deleteLesson() {
        val lessonId = randomUUID();
        doNothing().when(lessonService)
            .deleteLesson(courseId, lessonId);

        assertThat(courseController.deleteLesson(courseId, lessonId))
            .extracting(ResponseEntity::getBody, ResponseEntity::getStatusCode)
            .containsExactly(null, NO_CONTENT);

        verify(lessonService).deleteLesson(courseId, lessonId);
    }
}
