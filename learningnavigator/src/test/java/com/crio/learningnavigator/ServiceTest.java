package com.crio.learningnavigator;


import com.crio.learningnavigator.dto.StudentDto;
import com.crio.learningnavigator.exception.StudentExceptions;
import com.crio.learningnavigator.model.Exam;
import com.crio.learningnavigator.model.Student;
import com.crio.learningnavigator.model.Subject;
import com.crio.learningnavigator.repo.ExamRepo;
import com.crio.learningnavigator.repo.StudentRepo;
import com.crio.learningnavigator.repo.SubjectRepo;
import com.crio.learningnavigator.service.LnServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private ExamRepo examRepo;

    @Mock
    private SubjectRepo subjectRepo;

    @Mock
    private StudentDto studentDto;

    @InjectMocks
    private LnServiceImp lmsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        // Arrange
        when(studentRepo.findAll()).thenReturn(Collections.emptyList());

        // Act
        var students = lmsService.getAllStudents();

        // Assert
        assertTrue(students.isEmpty(), "Students list should be empty");
        verify(studentRepo, times(1)).findAll();
    }

    @Test
    public void testGetStudentById_whenStudentExists() {

        Student student = Student.builder()
                .studentId(1L)
                .name("John Doe")
                .build();
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(studentDto.mapDto(student)).thenReturn(new StudentDto("John Doe", null, null));

        StudentDto foundStudent = lmsService.getStudentById(1L);

        assertNotNull(foundStudent, "Student should not be null");
        assertEquals("John Doe", foundStudent.getName());
    }

    @Test
    public void testGetStudentById_whenStudentDoesNotExist() {
        // Arrange
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentExceptions.class, () -> {
            lmsService.getStudentById(1L);
        });

        String expectedMessage = "Student with ID: 1 does not exist";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testCreateStudent() {

        StudentDto studentDtoRequest = new StudentDto( "Jane Doe", null, null);
        Student student = new Student();
        student.setName("Jane Doe");
        when(studentDto.mapData(studentDtoRequest)).thenReturn(student);
        when(studentRepo.save(student)).thenReturn(student);
        when(studentDto.mapDto(student)).thenReturn(new StudentDto("Jane Doe", null, null));

        StudentDto createdStudent = lmsService.createStudent(studentDtoRequest);

        assertNotNull(createdStudent);
        assertEquals("Jane Doe", createdStudent.getName());
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    public void testUpdateStudent() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        StudentDto updatedStudentDto = new StudentDto("Updated Name", null, null);
        when(studentDto.mapDto(student)).thenReturn(updatedStudentDto);
        when(studentDto.mapData(updatedStudentDto)).thenReturn(student);
        when(studentRepo.save(student)).thenReturn(student);

        // Act
        StudentDto result = lmsService.updateStudent(1L, updatedStudentDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    public void testDeleteStudent() {

        doNothing().when(studentRepo).deleteById(1L);

        lmsService.deleteStudent(1L);

        verify(studentRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testRegisterStudentForExam_whenStudentNotEnrolledInSubject() {
        Long examId = 1L;
        Long studentId = 1L;
        Student student = new Student();
        Exam exam = new Exam();
        Subject subject = new Subject();
        exam.setSubject(subject);
        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(examRepo.findById(examId)).thenReturn(Optional.of(exam));

        boolean result = lmsService.registerStudentForExam(examId, studentId);


        assertFalse(result, "Student should not be able to register for the exam if not enrolled in the subject");
    }
}

