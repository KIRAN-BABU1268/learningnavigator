package com.crio.learningnavigator.repo;

import com.crio.learningnavigator.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam,Long> {
}
