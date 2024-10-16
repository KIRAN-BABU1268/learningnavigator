package com.crio.learningnavigator.controller;

import com.crio.learningnavigator.dto.ExamDto;
import com.crio.learningnavigator.dto.StudentDto;
import com.crio.learningnavigator.dto.SubjectDto;
import com.crio.learningnavigator.model.Exam;
import com.crio.learningnavigator.model.Student;
import com.crio.learningnavigator.model.Subject;
import com.crio.learningnavigator.service.LnService;
import com.crio.learningnavigator.service.LnServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    LnServiceImp lnService;

    @GetMapping("/hidden-feature/{number}")
    public ResponseEntity<String> getNumberFact(@PathVariable int number) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://numbersapi.com/" + number;
        String fact = restTemplate.getForObject(url, String.class);
        return new ResponseEntity<>(fact, HttpStatus.OK);
    }

    @GetMapping("/students")
    public List<StudentDto> getAllStudents() {
        return lnService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        StudentDto student = lnService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students/add")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentRequest) {
        StudentDto newStudent = lnService.createStudent(studentRequest);
        return ResponseEntity.ok(newStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto updatedStudent) {
        StudentDto student = lnService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        lnService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/subjects")
    public List<SubjectDto> getAllSubjects() {
        return lnService.getAllSubjects();
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long id) {
        SubjectDto subject = lnService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/subjects/add")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectRequest) {
        SubjectDto newSubject = lnService.createSubject(subjectRequest);
        return ResponseEntity.ok(newSubject);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        lnService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/exams")
    public List<ExamDto> getAllExams() {
        return lnService.getAllExams();
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<ExamDto> getExamById(@PathVariable Long id) {
        ExamDto exam = lnService.getExamById(id);
        return ResponseEntity.ok(exam);
    }

    @PostMapping("/exams/add")
    public ResponseEntity<Object> createExam(@RequestBody ExamDto examRequest) {
        ExamDto newExam = lnService.createExam(examRequest);
        return ResponseEntity.ok(newExam);
    }

    @PostMapping("/exams/{examId}")
    public ResponseEntity<String> registerStudentForExam(@PathVariable Long examId, @RequestParam Long studentId) {
        boolean registered = lnService.registerStudentForExam(examId, studentId);
        if (registered) {
            return ResponseEntity.ok("Student registered for the exam successfully.");
        } else {
            return ResponseEntity.badRequest().body("Student must be enrolled in the subject before registering for the exam.");
        }
    }

    @DeleteMapping("/exams/{id}")
    public ResponseEntity<Void> deleteExams(@PathVariable Long id) {
        lnService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
