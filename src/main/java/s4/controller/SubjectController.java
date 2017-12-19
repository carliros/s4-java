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
@RequestMapping("subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectStudentService subjectStudentService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/list")
    public List list() {
        return subjectService.getSubjects();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> createSubject(@RequestBody Subject subject) {

        Integer studentId = subjectService.createSubject(subject);
        return ResponseEntity.ok(new ResponseResult().returnValue("subjectId", studentId));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> editSubject(@RequestBody Subject newSubject) {

        Optional<Subject> subToEdit = subjectService.findSubject(newSubject.getId());

        if (!subToEdit.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Class does not exist."));
        }

        Optional<Integer> subjectEdited = subjectService.editSubject(newSubject);

        if (subjectEdited.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectEditedId", subjectEdited.get()));
        } else {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", String.format("Subject %d can not be edited", newSubject.getId())));
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> deleteSubject(@PathVariable Integer id) {

        Optional<Subject> subjectToDelete = subjectService.findSubject(id);

        if (!subjectToDelete.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Subject does not exist."));
        }

        Optional<Integer> subjectDeleted = subjectService.deleteSubject(subjectToDelete.get().getId());

        if (subjectDeleted.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectDeletedId", subjectDeleted.get()));
        } else {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", String.format("Subject %d can not be deleted", id)));
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

    @RequestMapping(value = "/retrieveSubjectsForStudent/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseResult> getSubjectsForStudent(@PathVariable Integer studentId) {
        Optional<Student> student = studentService.findStudent(studentId);
        if (!student.isPresent()) {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", "Student does not exist."));
        }

        List<Subject> subjectList = subjectStudentService.getSubjectsForStudent(studentId);
        return ResponseEntity.ok(new ResponseResult()
                .returnValue("subjectList", subjectList)
                .returnValue("student", student.get().getFirstName()));
    }
}
