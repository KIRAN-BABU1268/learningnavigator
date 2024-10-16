package com.crio.learningnavigator.repo;

import com.crio.learningnavigator.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
