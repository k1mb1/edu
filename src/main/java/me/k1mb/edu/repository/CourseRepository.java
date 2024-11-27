package me.k1mb.edu.repository;

import java.util.UUID;
import me.k1mb.edu.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {}
