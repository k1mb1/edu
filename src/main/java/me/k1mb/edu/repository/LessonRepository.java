package me.k1mb.edu.repository;

import me.k1mb.edu.repository.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findAllByCourseEntityId(UUID courseId);
}
