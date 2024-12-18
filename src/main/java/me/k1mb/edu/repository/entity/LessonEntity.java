package me.k1mb.edu.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id", nullable = false)
    UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    CourseEntity courseEntity;

    @Column(name = "title")
    String title;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "duration")
    Integer duration;
}
