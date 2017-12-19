package s4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s4.model.Student;
import s4.model.Subject;
import s4.services.StudentService;
import s4.services.SubjectService;
import s4.services.SubjectStudentService;
import s4.utils.ResponseResult;

import java.util.List;
import java.util.Optional;

/**
 * Created by carlos on 12/18/17.
 */
@RestController()
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectStudentService subjectStudentService;

    @RequestMapping("/list")
    public List list() {
        return studentService.getStudents();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> createStudent(@RequestBody Student student) {
        Integer studentId = studentService.createStudent(student.getFirstName(), student.getLastName());
        return ResponseEntity.ok(new ResponseResult().returnValue("studentId", studentId));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> editStudent(@RequestBody Student newStudent) {

        Optional<Student> studentToEdit = studentService.findStudent(newStudent.getId());

        if (!studentToEdit.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        Integer studentId = studentService.editStudent(newStudent);
        return ResponseEntity.ok(new ResponseResult().returnValue("editedStudentId", studentId));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> deleteStudent(@PathVariable Integer id) {

        Optional<Student> studentToDelete = studentService.findStudent(id);

        if (!studentToDelete.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        Integer studentId = studentService.deleteStudent(studentToDelete.get());
        return ResponseEntity.ok(new ResponseResult().returnValue("removedStudentId", studentId));
    }

    @RequestMapping(value = "/retrieveStudentsForClass/{subjectId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> getStudentsForClass(@PathVariable Integer subjectId) {
        Optional<Subject> subject = subjectService.findSubject(subjectId);
        if (!subject.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        List<Student> studentList = subjectStudentService.getStudentsForClass(subjectId);
        return ResponseEntity.ok(new ResponseResult()
                .returnValue("studentList", studentList)
                .returnValue("classTitle", subject.get().getTitle()));
    }
}
