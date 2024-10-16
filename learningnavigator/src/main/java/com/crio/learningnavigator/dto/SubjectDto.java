package com.crio.learningnavigator.dto;

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
public class SubjectDto {
    private String name;
    private Set<Student> students = new HashSet<>();

    public SubjectDto mapDto(Subject subject){
        return SubjectDto.builder()
                .name(subject.getName())
                .students(subject.getStudents())
                .build();
    }

    public Subject mapData(SubjectDto subjectDto){
        return Subject.builder()
                .name(subjectDto.getName())
                .students(subjectDto.getStudents())
                .build();
    }
}
