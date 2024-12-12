package me.k1mb.edu.service.impl;

import lombok.val;
import me.k1mb.edu.dto.CourseRequest;
import me.k1mb.edu.dto.CourseResponse;
import me.k1mb.edu.exception.ResourceNotFoundException;
import me.k1mb.edu.mapper.CourseMapper;
import me.k1mb.edu.model.Course;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для {@link CourseServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    final Course course = new Course().setId(randomUUID());
    final CourseResponse courseResponse = new CourseResponse(
        randomUUID(),
        "title",
        "description",
        randomUUID(),
        null,
        null);
    final CourseRequest courseRequest = new CourseRequest("title", "description", randomUUID());
    @Mock
    CourseRepository courseRepository;
    @Mock
    CourseMapper courseMapper;
    @InjectMocks
    CourseServiceImpl courseService;

    @Test
    void testGetAll() {
        doReturn(List.of(course)).when(courseRepository).findAll();
        doReturn(courseResponse).when(courseMapper).toDto(course);

        val result = courseService.getAll();

        assertThat(result)
            .isNotNull()
            .hasSize(1)
            .isEqualTo(List.of(courseResponse));
        verify(courseMapper).toDto(course);
        verify(courseRepository).findAll();
    }

    @Test
    void testGetById() {
        doReturn(of(course)).when(courseRepository).findById(course.getId());
        doReturn(courseResponse).when(courseMapper).toDto(course);

        val result = courseService.getById(course.getId());

        assertThat(result)
            .isNotNull()
            .isEqualTo(courseResponse);
        verify(courseMapper).toDto(course);
        verify(courseRepository).findById(course.getId());
    }

    @Test
    void testGetById_NotFound() {
        val id = randomUUID();
        doReturn(empty()).when(courseRepository).findById(id);

        assertThatThrownBy(() -> courseService.getById(id))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Course not found %s".formatted(id));
        verify(courseRepository).findById(id);
    }

    @Test
    void testCreateCourse() {
        doReturn(course).when(courseMapper).toEntity(courseRequest);
        doReturn(course).when(courseRepository).save(course);
        doReturn(courseResponse).when(courseMapper).toDto(course);

        val result = courseService.createCourse(courseRequest);

        assertThat(result)
            .isNotNull()
            .isEqualTo(courseResponse);
        verify(courseMapper).toEntity(courseRequest);
        verify(courseRepository).save(course);
        verify(courseMapper).toDto(course);
    }

    @Test
    void testUpdateCourse() {
        doReturn(of(course)).when(courseRepository).findById(course.getId());
        doNothing().when(courseMapper).partialUpdate(courseRequest, course);
        doReturn(course).when(courseRepository).save(course);
        doReturn(courseResponse).when(courseMapper).toDto(course);

        val result = courseService.updateCourse(course.getId(), courseRequest);

        assertThat(result)
            .isNotNull()
            .isEqualTo(courseResponse);
        verify(courseRepository).findById(course.getId());
        verify(courseMapper).partialUpdate(courseRequest, course);
        verify(courseRepository).save(course);
        verify(courseMapper).toDto(course);
    }

    @Test
    void testUpdateCourse_NotFound() {
        val id = randomUUID();
        doReturn(empty()).when(courseRepository).findById(id);

        assertThatThrownBy(() -> courseService.updateCourse(id, courseRequest))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Course not found %s".formatted(id));
        verify(courseRepository).findById(id);
    }

    @Test
    void testDeleteCourse() {
        doReturn(true).when(courseRepository).existsById(course.getId());

        courseService.deleteCourse(course.getId());

        verify(courseRepository).deleteById(course.getId());
    }

    @Test
    void testDeleteCourse_NotFound() {
        val id = randomUUID();
        doReturn(false).when(courseRepository).existsById(id);

        assertThatThrownBy(() -> courseService.deleteCourse(id))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Course not found %s".formatted(id));
        verify(courseRepository).existsById(id);
    }

    @Test
    void testCheckAuthor() {
        val authorId = randomUUID();
        val user = new User().setId(authorId);
        val courseId = randomUUID();
        course.setAuthor(user);

        doReturn(of(course)).when(courseRepository).findById(courseId);

        val result = courseService.checkAuthor(courseId);

        assertThat(result)
            .isNotNull()
            .isEqualTo(authorId);
        verify(courseRepository).findById(courseId);
    }

    @Test
    void testCheckAuthor_NotFound() {
        val courseId = randomUUID();
        doReturn(empty()).when(courseRepository).findById(courseId);

        assertThatThrownBy(() -> courseService.checkAuthor(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Course not found %s".formatted(courseId));
        verify(courseRepository).findById(courseId);
    }
}