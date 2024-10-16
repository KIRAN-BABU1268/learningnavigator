package com.crio.learningnavigator.repo;

import com.crio.learningnavigator.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject,Long> {
}
