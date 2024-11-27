package me.k1mb.edu.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

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
