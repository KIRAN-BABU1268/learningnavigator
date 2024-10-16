package com.crio.learningnavigator.dto;

import com.crio.learningnavigator.model.Exam;
import com.crio.learningnavigator.model.Student;
import com.crio.learningnavigator.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Subject subject;

    private Set<Student> enrolledStudents = new HashSet<>();

    public ExamDto mapDto(Exam exam){
        return ExamDto.builder()
                .subject(exam.getSubject())
                .enrolledStudents(exam.getEnrolledStudents())
                .build();
    }

    public Exam mapData(ExamDto examDto){
        return Exam.builder()
                .subject(examDto.getSubject())
                .enrolledStudents(examDto.getEnrolledStudents())
                .build();
    }
}
