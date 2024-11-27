package me.k1mb.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.k1mb.edu.DTO.CourseDtoRequest;
import me.k1mb.edu.DTO.CourseDtoResponse;
import me.k1mb.edu.DTO.LessonDtoRequest;
import me.k1mb.edu.DTO.LessonDtoResponse;
import me.k1mb.edu.service.CourseService;
import me.k1mb.edu.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/courses", produces = "application/json")
@Tag(name = "Courses", description = "API for managing courses")
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @Operation(
            summary = "Get a list of all courses",
            description = "Returns a list of all available courses.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of courses"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CourseDtoResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll());
    }

    @Operation(
            summary = "Get course details",
            description = "Returns details of a specific course by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved course details"),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @PreAuthorize(
            "hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseService.checkAuthor(#course_id).toString())")
    @GetMapping("/{course_id}")
    public ResponseEntity<CourseDtoResponse> getById(
            @Parameter(description = "Course ID", required = true) @PathVariable("course_id") UUID course_id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getById(course_id));
    }

    @Operation(
            summary = "Create a new course",
            description = "Creates a new course with the specified parameters.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Successfully created the course"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and authentication.name == #course.authorId.toString())")
    @PostMapping
    public ResponseEntity<CourseDtoResponse> createCourse(@Valid @RequestBody CourseDtoRequest course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(course));
    }

    @Operation(
            summary = "Delete a course",
            description = "Delete a specific course by its ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Course successfully deleted"),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
            })
    @PreAuthorize(
            "hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseService.checkAuthor(#course_id).toString())")
    @DeleteMapping("/{course_id}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "Course ID", required = true) @PathVariable("course_id") UUID course_id) {
        courseService.deleteCourse(course_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Update an existing course",
            description = "Update an existing course with the provided details",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Course successfully updated"),
                @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
            })
    @PreAuthorize(
            "hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseService.checkAuthor(#course_id).toString() and authentication.name == #course.authorId.toString())")
    @PutMapping("/{course_id}")
    public ResponseEntity<CourseDtoResponse> updateCourse(
            @Parameter(description = "Course ID", required = true) @PathVariable("course_id") UUID course_id,
            @Valid @RequestBody CourseDtoRequest course) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(course_id, course));
    }

    @Operation(
            summary = "Get all lessons by course ID",
            description = "Retrieve a list of all lessons for a specific course",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of lessons"),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
            })
    @PreAuthorize(
            "hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseService.checkAuthor(#course_id).toString())")
    @GetMapping("/{course_id}/lessons")
    public ResponseEntity<List<LessonDtoResponse>> getAllLessonsByCourseId(
            @Parameter(description = "Course ID", required = true) @PathVariable("course_id") UUID course_id) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.getAllByCourseId(course_id));
    }

    @Operation(
            summary = "Create a new lesson for a course",
            description = "Create a new lesson for a specific course",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Lesson successfully created"),
                @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                @ApiResponse(responseCode = "404", description = "Course not found", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            })
    @PreAuthorize(
            "hasRole('ADMIN') or (hasRole('USER') and authentication.name == @courseService.checkAuthor(#course_id).toString())")
    @PostMapping("/{course_id}/lessons")
    public ResponseEntity<LessonDtoResponse> createLesson(
            @Parameter(description = "Course ID", required = true) @PathVariable("course_id") UUID course_id,
            @Valid @RequestBody LessonDtoRequest lesson) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.createLesson(course_id, lesson));
    }
}