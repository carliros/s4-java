package s4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s4.model.Student;
import s4.model.Subject;
import s4.services.StudentService;
import s4.services.SubjectService;
import s4.utils.ResponseResult;

import java.util.Optional;

/**
 * Created by carlos on 12/18/17.
 */
@RestController()
@RequestMapping
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/classes")
    public ResponseEntity<ResponseResult> list(@RequestParam(value = "code") Optional<String> code,
                                               @RequestParam(value = "title") Optional<String> title,
                                               @RequestParam(value = "description") Optional<String> description,
                                               @RequestParam(value = "studentId") Optional<Integer> studentId) {

        if (studentId.isPresent() && ! studentService.findStudent(studentId.get()).isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        return ResponseEntity.ok(new ResponseResult()
                .returnValue("classes", subjectService.getSubjects(code, title, description, studentId)));
    }

    @RequestMapping(value = "/classes", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> createSubject(@RequestBody Subject subject) {

        Integer studentId = subjectService.createSubject(subject);
        return ResponseEntity.ok(new ResponseResult().returnValue("subjectId", studentId));
    }

    @RequestMapping(value = "/classes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseResult> editSubject(@PathVariable Integer id, @RequestBody Subject newSubject) {

        Optional<Subject> subToEdit = subjectService.findSubject(id);

        if (!subToEdit.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        newSubject.setId(id);
        Optional<Integer> subjectEdited = subjectService.editSubject(newSubject);

        if (subjectEdited.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectEditedId", subjectEdited.get()));
        } else {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", String.format("Subject %d can not be edited", newSubject.getId())));
        }
    }

    @RequestMapping(value = "/classes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseResult> deleteSubject(@PathVariable Integer id) {

        Optional<Subject> subjectToDelete = subjectService.findSubject(id);

        if (!subjectToDelete.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        Optional<Integer> subjectDeleted = subjectService.deleteSubject(subjectToDelete.get().getId());

        if (subjectDeleted.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectDeletedId", subjectDeleted.get()));
        } else {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", String.format("Class %d can not be deleted", id)));
        }
    }

    @RequestMapping(value = "/register/{subjectId}/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> registerStudent(@PathVariable Integer subjectId, @PathVariable Integer studentId) {
        Optional<Subject> subject = subjectService.findSubject(subjectId);
        if (!subject.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        Optional<Student> student = studentService.findStudent(studentId);
        if (!student.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        subjectService.registerStudent(subjectId, studentId);
        return ResponseEntity.ok(new ResponseResult().returnValue("message", String.format("Student %s registered for class %s", student.get().getFirstName(), subject.get().getTitle())));
    }
}
