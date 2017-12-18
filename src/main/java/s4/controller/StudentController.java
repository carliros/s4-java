package s4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s4.services.StudentService;

import java.util.List;

/**
 * Created by carlos on 12/18/17.
 */
@RestController()
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/list")
    public List list() {
        return studentService.getStudents();
    }
}
