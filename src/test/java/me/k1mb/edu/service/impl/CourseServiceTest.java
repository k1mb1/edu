package me.k1mb.edu.service.impl;

import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;
import me.k1mb.edu.dto.CourseMapper;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    final Course course = new Course().setId(randomUUID());
    final CourseDtoResponse courseDtoResponse = new CourseDtoResponse(
        randomUUID(),
        "title",
        "description",
        randomUUID(),
        null,
        null);
    final CourseDtoRequest courseDtoRequest = new CourseDtoRequest("title", "description", randomUUID());
    @Mock
    CourseRepository courseRepository;
    @Mock
    CourseMapper courseMapper;
    @InjectMocks
    CourseServiceImpl courseService;

    @Test
    void testGetAll() {
        doReturn(List.of(course)).when(courseRepository).findAll();
        doReturn(courseDtoResponse).when(courseMapper).toDto(course);

        List<CourseDtoResponse> result = courseService.getAll();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(courseDtoResponse, result.getFirst()));
    }

    @Test
    void testGetById() {
        doReturn(Optional.of(course)).when(courseRepository).findById(course.getId());
        doReturn(courseDtoResponse).when(courseMapper).toDto(course);

        CourseDtoResponse result = courseService.getById(course.getId());

        assertEquals(courseDtoResponse, result);
    }

    @Test
    void testGetById_NotFound() {
        UUID id = randomUUID();
        doReturn(Optional.empty()).when(courseRepository).findById(id);

        assertThrows(ResourceNotFoundException.class, () -> courseService.getById(id));
    }

    @Test
    void testCreateCourse() {
        doReturn(course).when(courseMapper).toEntity(courseDtoRequest);
        doReturn(course).when(courseRepository).save(course);
        doReturn(courseDtoResponse).when(courseMapper).toDto(course);

        CourseDtoResponse result = courseService.createCourse(courseDtoRequest);

        assertEquals(courseDtoResponse, result);
    }

    @Test
    void testUpdateCourse() {
        doReturn(Optional.of(course)).when(courseRepository).findById(course.getId());
        doNothing().when(courseMapper).partialUpdate(courseDtoRequest, course);
        doReturn(course).when(courseRepository).save(course);
        doReturn(courseDtoResponse).when(courseMapper).toDto(course);

        CourseDtoResponse result = courseService.updateCourse(course.getId(), courseDtoRequest);

        assertEquals(courseDtoResponse, result);
        verify(courseMapper).partialUpdate(courseDtoRequest, course);
    }

    @Test
    void testUpdateCourse_NotFound() {
        UUID id = randomUUID();
        doReturn(Optional.empty()).when(courseRepository).findById(id);

        assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(id, courseDtoRequest));
    }

    @Test
    void testDeleteCourse() {
        doReturn(true).when(courseRepository).existsById(course.getId());

        courseService.deleteCourse(course.getId());

        verify(courseRepository).deleteById(course.getId());
    }

    @Test
    void testDeleteCourse_NotFound() {
        UUID id = randomUUID();
        when(courseRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(id));
    }

    @Test
    void testCheckAuthor() {
        UUID authorId = randomUUID();
        User user = new User().setId(authorId);
        UUID courseId = randomUUID();
        course.setAuthor(user);

        doReturn(Optional.of(course)).when(courseRepository).findById(courseId);

        UUID result = courseService.checkAuthor(courseId);

        assertEquals(authorId, result);
    }

    @Test
    void testCheckAuthor_NotFound() {
        UUID courseId = randomUUID();
        doReturn(Optional.empty()).when(courseRepository).findById(courseId);

        assertThrows(ResourceNotFoundException.class, () -> courseService.checkAuthor(courseId));
    }
}