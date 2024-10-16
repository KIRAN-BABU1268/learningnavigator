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

public class StudentDto {
    private String name;
    private Set<Subject> enrolledSubjects = new HashSet<>();
    private Set<Exam> registeredExams = new HashSet<>();

    public StudentDto mapDto(Student student)
    {
        return StudentDto.builder()
                .name(student.getName())
                .enrolledSubjects(student.getEnrolledSubjects())
                .registeredExams(student.getRegisteredExams())
                .build();
    }

    public Student mapData(StudentDto studentDto){
        return Student.builder()
                .name(studentDto.getName())
                .enrolledSubjects(studentDto.getEnrolledSubjects())
                .registeredExams(studentDto.getRegisteredExams()).build();
    }
}
