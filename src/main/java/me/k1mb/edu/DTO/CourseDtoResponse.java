package me.k1mb.edu.DTO;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Value;

/** DTO for {@link me.k1mb.edu.model.Course} */
@Value
public class CourseDtoResponse {
    UUID id;
    String title;
    String description;
    UUID authorId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
