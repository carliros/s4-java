package s4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s4.model.Student;
import s4.services.StudentService;
import s4.services.SubjectService;
import s4.utils.ResponseResult;

import java.util.Optional;

/**
 * Created by carlos on 12/18/17.
 */
@RestController()
@RequestMapping
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<ResponseResult> list(@RequestParam(value = "firstName") Optional<String> firstName,
                                               @RequestParam(value = "lastName") Optional<String> lastName,
                                               @RequestParam(value = "classId") Optional<Integer> classId) {

        if (classId.isPresent() && ! subjectService.findSubject(classId.get()).isPresent()){
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        return ResponseEntity.ok(new ResponseResult()
                .returnValue("students", studentService.getStudents(firstName, lastName, classId)));
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> createStudent(@RequestBody Student student) {
        Integer studentId = studentService.createStudent(student.getFirstName(), student.getLastName());
        return ResponseEntity.ok(new ResponseResult().returnValue("studentId", studentId));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseResult> editStudent(@PathVariable Integer id, @RequestBody Student newStudent) {

        Optional<Student> studentToEdit = studentService.findStudent(id);

        if (!studentToEdit.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        newStudent.setId(id);
        Integer studentId = studentService.editStudent(newStudent);
        return ResponseEntity.ok(new ResponseResult().returnValue("editedStudentId", studentId));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseResult> deleteStudent(@PathVariable Integer id) {

        Optional<Student> studentToDelete = studentService.findStudent(id);

        if (!studentToDelete.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        Integer studentId = studentService.deleteStudent(studentToDelete.get());
        return ResponseEntity.ok(new ResponseResult().returnValue("removedStudentId", studentId));
    }
}
