package me.k1mb.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.k1mb.edu.controller.mapper.CourseRequestResponseMapper;
import me.k1mb.edu.controller.mapper.LessonRequestResponseMapper;
import me.k1mb.edu.controller.model.*;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static me.k1mb.edu.controller.utils.AuthorizationTypeUtil.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@RequestMapping(value = "/api/v1/courses", produces = "application/json")
@ApiResponse(responseCode = "400", description = "Неверный запрос",
    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
@ApiResponse(responseCode = "401", description = "Не авторизован")
@ApiResponse(responseCode = "403", description = "Запрещено",
    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
@Tag(name = "Курсы", description = "API для управления курсами")
public class CourseController {
    CourseService courseService;
    CourseRequestResponseMapper courseMapper;
    LessonService lessonService;
    LessonRequestResponseMapper lessonMapper;

    @Operation(summary = "Получить список всех курсов", description = "Возвращает список всех доступных курсов.")
    @ApiResponse(responseCode = "200", description = "Список курсов успешно получен")
    @PreAuthorize(ROLE_ADMIN)
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        return ResponseEntity.status(OK)
            .body(courseService.getAll().stream()
                .map(courseMapper::toResponse).toList());
    }

    @Operation(summary = "Получить детали курса", description = "Возвращает детали конкретного курса по его ID.")
    @ApiResponse(responseCode = "200", description = "Детали курса успешно получены")
    @ApiResponse(responseCode = "404", description = "Не найдено",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR)
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getById(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId) {

        return ResponseEntity.status(OK)
            .body(courseMapper.toResponse(
                courseService.getById(courseId)));
    }

    @Operation(summary = "Создать новый курс", description = "Создает новый курс с указанными параметрами.")
    @ApiResponse(responseCode = "201", description = "Курс успешно создан")
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + IS_AUTHOR)
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody final CourseRequest course) {
        return ResponseEntity.status(CREATED)
            .body(courseMapper.toResponse(
                courseService.createCourse(courseMapper.toDto(course))));
    }

    @Operation(summary = "Удалить курс", description = "Удаляет конкретный курс по его ID.")
    @ApiResponse(responseCode = "204", description = "Курс успешно удален")
    @ApiResponse(responseCode = "404", description = "Не найдено",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR)
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId) {

        courseService.deleteCourse(courseId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Operation(summary = "Обновить существующий курс", description = "Обновляет существующий курс с предоставленными данными.")
    @ApiResponse(responseCode = "200", description = "Курс успешно обновлен")
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR + AND + IS_AUTHOR)
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId,
        @Valid
        @RequestBody final CourseRequest course) {

        return ResponseEntity.status(OK)
            .body(courseMapper.toResponse(
                courseService.updateCourse(courseId, courseMapper.toDto(course))));
    }

    @Operation(summary = "Получить все уроки по ID курса", description = "Получает список всех уроков для конкретного курса.")
    @ApiResponse(responseCode = "200", description = "Список уроков успешно получен")
    @ApiResponse(responseCode = "404", description = "Не найдено",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR)
    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<LessonResponse>> getAllLessonsByCourseId(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId) {

        return ResponseEntity.status(OK)
            .body(lessonService.getAllByCourseId(courseId).stream()
                .map(lessonMapper::toResponse).toList());
    }

    @Operation(summary = "Создать новый урок для курса", description = "Создает новый урок для конкретного курса.")
    @ApiResponse(responseCode = "201", description = "Урок успешно создан")
    @ApiResponse(responseCode = "404", description = "Не найдено",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR)
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<LessonResponse> createLesson(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId,
        @Valid
        @RequestBody final LessonRequest lesson) {

        return ResponseEntity.status(CREATED)
            .body(lessonMapper.toResponse(
                lessonService.createLesson(courseId, lessonMapper.toDto(lesson))));
    }

    @Operation(summary = "Удалить урок по ID урока", description = "Удаляет конкретный урок по его ID с конкретного курса.")
    @ApiResponse(responseCode = "204", description = "Урок успешно удален")
    @ApiResponse(responseCode = "404", description = "Не найдено",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @PreAuthorize(ROLE_ADMIN + OR + ROLE_USER + AND + CHECK_COURSE_AUTHOR)
    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> deleteLesson(
        @Parameter(description = "ID курса", required = true)
        @PathVariable("courseId") final UUID courseId,
        @Parameter(description = "ID занятия", required = true)
        @PathVariable("lessonId") final UUID lessonId) {

        lessonService.deleteLesson(courseId, lessonId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}