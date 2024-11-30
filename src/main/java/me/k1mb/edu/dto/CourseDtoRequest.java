package me.k1mb.edu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 * DTO for {@link me.k1mb.edu.model.Course}
 */
@Data
public class CourseDtoRequest {
    @NotBlank
    @Size(max = 255)
    String title;

    @NotBlank
    String description;

    @NotNull
    UUID authorId;
}