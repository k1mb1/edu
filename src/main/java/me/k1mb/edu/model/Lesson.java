package me.k1mb.edu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Column(name = "title")
    String title;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "duration")
    Integer duration;
}
