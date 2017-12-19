package s4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s4.model.Subject;
import s4.services.SubjectService;
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

        if (subjectEdited.isPresent()){
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectEditedId", subjectEdited.get()));
        }else {
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

        if (subjectDeleted.isPresent()){
            return ResponseEntity.ok(new ResponseResult().returnValue("subjectDeletedId", subjectDeleted.get()));
        }else {
            return ResponseEntity.ok(new ResponseResult().returnValue("error", String.format("Subject %d can not be deleted", id)));
        }
    }
}
