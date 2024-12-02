package me.k1mb.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.k1mb.edu.dto.CourseDtoRequest;
import me.k1mb.edu.dto.CourseDtoResponse;
import me.k1mb.edu.dto.LessonDtoRequest;
import me.k1mb.edu.dto.LessonDtoResponse;
import me.k1mb.edu.exeption.ErrorMessage;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@RequestMapping(value = "/api/v1/courses", produces = "application/json")
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Bad Request",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden",
        content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
@Tag(name = "Courses", description = "API for managing courses")
public class CourseController {
    CourseService courseService;
    LessonService lessonService;

    @Operation(summary = "Get a list of all courses", description = "Returns a list of all available courses.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of courses")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CourseDtoResponse>> getAll() {
        return ResponseEntity.status(OK)
            .body(courseService.getAll());
    }

    @Operation(summary = "Get course details", description = "Returns details of a specific course by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved course details"),
        @ApiResponse(responseCode = "404", description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString())")
    @GetMapping("/{course_id}")
    public ResponseEntity<CourseDtoResponse> getById(
        @Parameter(description = "Course ID", required = true) @PathVariable("course_id") final UUID course_id) {

        return ResponseEntity.status(OK)
            .body(courseService.getById(course_id));
    }

    @Operation(summary = "Create a new course", description = "Creates a new course with the specified parameters.")
    @ApiResponse(responseCode = "201", description = "Successfully created the course")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == #course.authorId.toString())")
    @PostMapping
    public ResponseEntity<CourseDtoResponse> createCourse(@Valid @RequestBody final CourseDtoRequest course) {

        return ResponseEntity.status(CREATED)
            .body(courseService.createCourse(course));
    }

    @Operation(summary = "Delete a course", description = "Delete a specific course by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Course successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString())")
    @DeleteMapping("/{course_id}")
    public ResponseEntity<Void> deleteCourse(
        @Parameter(description = "Course ID", required = true) @PathVariable("course_id") final UUID course_id) {

        courseService.deleteCourse(course_id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Operation(summary = "Update an existing course", description = "Update an existing course with the provided details")
    @ApiResponse(responseCode = "200", description = "Course successfully updated")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString() and authentication.name == #course.authorId.toString())")
    @PutMapping("/{course_id}")
    public ResponseEntity<CourseDtoResponse> updateCourse(
        @Parameter(description = "Course ID", required = true) @PathVariable("course_id") final UUID course_id,
        @Valid @RequestBody final CourseDtoRequest course) {

        return ResponseEntity.status(OK)
            .body(courseService.updateCourse(course_id, course));
    }

    @Operation(summary = "Get all lessons by course ID", description = "Retrieve a list of all lessons for a specific course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of lessons"),
        @ApiResponse(responseCode = "404", description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString())")
    @GetMapping("/{course_id}/lessons")
    public ResponseEntity<List<LessonDtoResponse>> getAllLessonsByCourseId(
        @Parameter(description = "Course ID", required = true) @PathVariable("course_id") final UUID course_id) {

        return ResponseEntity.status(OK)
            .body(lessonService.getAllByCourseId(course_id));
    }

    @Operation(summary = "Create a new lesson for a course", description = "Create a new lesson for a specific course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lesson successfully created"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString())")
    @PostMapping("/{course_id}/lessons")
    public ResponseEntity<LessonDtoResponse> createLesson(
        @Parameter(description = "Course ID", required = true) @PathVariable("course_id") final UUID course_id,
        @Valid @RequestBody final LessonDtoRequest lesson) {

        return ResponseEntity.status(CREATED)
            .body(lessonService.createLesson(course_id, lesson));
    }
}
