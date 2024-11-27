package me.k1mb.edu.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Value;

/** DTO for {@link me.k1mb.edu.model.Lesson} */
@Value
public class LessonDtoRequest {
    @NotNull
    UUID courseId;

    @NotBlank
    String title;

    @NotBlank
    String description;

    @Positive
    Integer duration;
}
