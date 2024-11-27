package me.k1mb.edu.DTO;

import java.util.UUID;
import lombok.Value;

/** DTO for {@link me.k1mb.edu.model.Lesson} */
@Value
public class LessonDtoResponse {
    UUID id;
    UUID courseId;
    String title;
    String description;
    Integer duration;
}
