package com.crio.learningnavigator.service;

import com.crio.learningnavigator.dto.ExamDto;
import com.crio.learningnavigator.dto.StudentDto;
import com.crio.learningnavigator.dto.SubjectDto;
import com.crio.learningnavigator.exception.ExamExceptions;
import com.crio.learningnavigator.exception.StudentExceptions;
import com.crio.learningnavigator.exception.SubjectExceptions;
import com.crio.learningnavigator.model.Exam;
import com.crio.learningnavigator.model.Student;
import com.crio.learningnavigator.model.Subject;
import com.crio.learningnavigator.repo.ExamRepo;
import com.crio.learningnavigator.repo.StudentRepo;
import com.crio.learningnavigator.repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LnServiceImp implements LnService {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    StudentDto studentDto;

    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    SubjectDto subjectDto;

    @Autowired
    ExamRepo examRepo;
    @Autowired
    ExamDto examDto;

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepo.findAll()
                .stream()
                .map(student ->
                        studentDto.mapDto(student))
                .toList();

    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentExceptions("Student with ID: " + id + " does not exist"));
        return studentDto.mapDto(student);
    }

    @Override
    public StudentDto createStudent(StudentDto studentRequest) {
        Student student = studentRepo.save(studentDto.mapData(studentRequest));
        return studentDto.mapDto(student);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        StudentDto studentDto1 = getStudentById(id);
        studentDto1.setName(updatedStudent.getName());
        studentDto1.setEnrolledSubjects(updatedStudent.getEnrolledSubjects());
        studentDto1.setRegisteredExams(updatedStudent.getRegisteredExams());
        studentRepo.save(studentDto.mapData(studentDto1));
        return studentDto1 ;
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public List<SubjectDto> getAllSubjects() {

        return subjectRepo.findAll().stream()
                .map(subject -> subjectDto.mapDto(subject))
                .toList();
    }

    @Override
    public SubjectDto getSubjectById(Long id) {
        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() -> new SubjectExceptions("Student with ID: " + id + " does not exist"));
        return subjectDto.mapDto(subject);
    }

    @Override
    public SubjectDto createSubject(SubjectDto subjectRequest) {
        Subject subject = subjectRepo.save(subjectDto.mapData(subjectRequest));
        return subjectDto.mapDto(subject);
    }

    @Override
    public SubjectDto updateSubject(Long id ,SubjectDto subjectRequest){
        SubjectDto subjectDto1 = getSubjectById(id);
        subjectDto1.setName(subjectRequest.getName());
        subjectDto1.setStudents(subjectRequest.getStudents());
        subjectRepo.save(subjectDto.mapData(subjectDto1));
        return subjectDto1;
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepo.deleteById(id);
    }


    @Override
    public List<ExamDto> getAllExams() {
        return examRepo.findAll().stream()
                .map(exam -> examDto.mapDto(exam))
                .toList();
    }

    @Override
    public ExamDto createExam(ExamDto examRequest) {
        Exam exam = examRepo.save(examDto.mapData(examRequest));
        return examDto.mapDto(exam);
    }

    @Override
    public ExamDto getExamById(Long id) {
        Exam exam = examRepo.findById(id)
                .orElseThrow(() -> new ExamExceptions("Student with ID: " + id + " does not exist"));
        return examDto.mapDto(exam);
    }

    @Override
    public void deleteExam(Long id) {
        examRepo.deleteById(id);
    }

    @Override
    public boolean registerStudentForExam(Long examId, Long studentId) {
        Optional<Exam> examOpt = examRepo.findById(examId);
        Optional<Student> studentOpt = studentRepo.findById(studentId);

        if (!examOpt.isPresent() || !studentOpt.isPresent()) {
            throw new ExamExceptions("Student or Exam not found");
        }
        Exam exam = examOpt.get();
        Student student = studentOpt.get();

        Subject subject = exam.getSubject();
        if (!student.getEnrolledSubjects().contains(subject)) {
            return false;
        }

        exam.getEnrolledStudents().add(student);
        student.getRegisteredExams().add(exam);

        examRepo.save(exam);
        studentRepo.save(student);
        return true;
    }
}
