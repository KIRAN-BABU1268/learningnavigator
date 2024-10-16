package com.crio.learningnavigator.service;
import com.crio.learningnavigator.dto.ExamDto;
import com.crio.learningnavigator.dto.StudentDto;
import com.crio.learningnavigator.dto.SubjectDto;
import com.crio.learningnavigator.model.Exam;
import com.crio.learningnavigator.model.Student;
import com.crio.learningnavigator.model.Subject;

import java.util.List;

public interface LnService {

    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);
    StudentDto createStudent(StudentDto studentDto);
    StudentDto updateStudent(Long id, StudentDto updatedStudent);
    void deleteStudent(Long id);

    List<SubjectDto> getAllSubjects();
    SubjectDto getSubjectById(Long id);
    SubjectDto createSubject(SubjectDto subjectDto);
    SubjectDto updateSubject(Long id, SubjectDto subjectDto);
    void deleteSubject(Long id);

    List<ExamDto> getAllExams();
    ExamDto createExam(ExamDto examDto);
    ExamDto getExamById(Long id);
    void deleteExam(Long id);
    boolean registerStudentForExam(Long examId, Long studentId);
}
