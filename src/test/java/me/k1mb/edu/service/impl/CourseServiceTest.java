package me.k1mb.edu.service.impl;

import lombok.val;
import me.k1mb.edu.repository.CourseRepository;
import me.k1mb.edu.repository.entity.CourseEntity;
import me.k1mb.edu.repository.entity.UserEntity;
import me.k1mb.edu.service.exception.ResourceNotFoundException;
import me.k1mb.edu.service.mapper.CourseEntityMapper;
import me.k1mb.edu.service.model.CourseDto;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для {@link CourseServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    final String message = "Курс с id=%s не найден";
    final CourseEntity courseEntity = CourseEntity.builder().id(randomUUID()).build();
    final CourseDto courseDto = new CourseDto(
        randomUUID(),
        "title",
        "description",
        randomUUID(),
        null,
        null
    );
    @Mock
    CourseRepository courseRepository;
    @Mock
    CourseEntityMapper courseMapper;
    @InjectMocks
    CourseServiceImpl courseService;


    @Test
    void testGetAll() {
        when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
        when(courseMapper.toDto(courseEntity)).thenReturn(courseDto);

        assertThat(courseService.getAll())
            .hasSize(1)
            .isEqualTo(List.of(courseDto));

        verify(courseMapper).toDto(courseEntity);
        verify(courseRepository).findAll();
    }

    @Test
    void testGetById() {
        when(courseRepository.findById(courseEntity.getId()))
            .thenReturn(of(courseEntity));
        when(courseMapper.toDto(courseEntity))
            .thenReturn(courseDto);

        assertThat(courseService.getById(courseEntity.getId()))
            .isEqualTo(courseDto);

        verify(courseMapper).toDto(courseEntity);
        verify(courseRepository).findById(courseEntity.getId());
    }

    @Test
    void testGetByIdNotFound() {
        val id = randomUUID();
        when(courseRepository.findById(id))
            .thenReturn(empty());

        assertThatThrownBy(() -> courseService.getById(id))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message.formatted(id));

        verify(courseRepository).findById(id);
    }

    @Test
    void testCreateCourse() {
        when(courseMapper.toEntity(courseDto))
            .thenReturn(courseEntity);
        when(courseRepository.save(courseEntity))
            .thenReturn(courseEntity);
        when(courseMapper.toDto(courseEntity))
            .thenReturn(courseDto);

        assertThat(courseService.createCourse(courseDto))
            .isEqualTo(courseDto);

        verify(courseMapper).toEntity(courseDto);
        verify(courseRepository).save(courseEntity);
        verify(courseMapper).toDto(courseEntity);
    }

    @Test
    void testUpdateCourse() {
        when(courseRepository.findById(courseEntity.getId()))
            .thenReturn(of(courseEntity));
        when(courseRepository.save(courseEntity))
            .thenReturn(courseEntity);
        when(courseMapper.toDto(courseEntity))
            .thenReturn(courseDto);


        assertThat(courseService.updateCourse(courseEntity.getId(), courseDto))
            .isEqualTo(courseDto);

        verify(courseRepository).findById(courseEntity.getId());
        verify(courseMapper).partialUpdate(courseDto, courseEntity);
        verify(courseRepository).save(courseEntity);
        verify(courseMapper).toDto(courseEntity);
    }

    @Test
    void testUpdateCourseNotFound() {
        val id = randomUUID();
        when(courseRepository.findById(id))
            .thenReturn(empty());

        assertThatThrownBy(() -> courseService.updateCourse(id, courseDto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message.formatted(id));

        verify(courseRepository).findById(id);
    }

    @Test
    void testDeleteCourse() {
        when(courseRepository.existsById(courseEntity.getId()))
            .thenReturn(true);

        courseService.deleteCourse(courseEntity.getId());

        verify(courseRepository).deleteById(courseEntity.getId());
    }

    @Test
    void testDeleteCourseNotFound() {
        val id = randomUUID();
        when(courseRepository.existsById(id))
            .thenReturn(false);

        assertThatThrownBy(() -> courseService.deleteCourse(id))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message.formatted(id));

        verify(courseRepository).existsById(id);
    }

    @Test
    void testCheckAuthor() {
        val authorId = randomUUID();
        val user = new UserEntity().setId(authorId);
        val courseId = randomUUID();
        courseEntity.setAuthor(user);

        when(courseRepository.findById(courseId))
            .thenReturn(of(courseEntity));

        assertThat(courseService.checkAuthor(courseId))
            .isEqualTo(authorId);

        verify(courseRepository).findById(courseId);
    }

    @Test
    void testCheckAuthorNotFound() {
        val courseId = randomUUID();
        when(courseRepository.findById(courseId))
            .thenReturn(empty());

        assertThatThrownBy(() -> courseService.checkAuthor(courseId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage(message.formatted(courseId));

        verify(courseRepository).findById(courseId);
    }
}