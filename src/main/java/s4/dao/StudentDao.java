package s4.dao;

/**
 * Created by carlos on 12/18/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import s4.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StudentDao {
    //mocking persistence layer here

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger idGenerator = new AtomicInteger(0);

    private List<Student> studentList = new ArrayList<>();

    public List<Student> getStudentList() {
        return studentList;
    }

    public Integer createStudent(String firsName, String lastName) {
        Student student = new Student();
        student.setFirstName(firsName);
        student.setLastName(lastName);
        student.setId(idGenerator.incrementAndGet());

        studentList.add(student);

        return student.getId();
    }

    public Optional<Student> findStudent(Integer id) {
        for (Student std : studentList) {
            if (std.getId().equals(id)) {
                return Optional.of(std);
            }
        }

        return Optional.empty();
    }

    public Integer removeStudent(Integer id) {
        for (int index = 0; index < studentList.size(); index++) {
            Student std = studentList.get(index);
            if (std.getId().equals(id)) {
                studentList.remove(index);
                log.info("Student removed.");
            }
        }

        return id;
    }

    public Integer editStudent(Student std) {
        for (int index = 0; index < studentList.size(); index++) {
            Student old = studentList.get(index);
            if (old.getId().equals(std.getId())) {
                old.setFirstName(std.getFirstName());
                old.setLastName(std.getLastName());

                log.info("Student edited.");
            }
        }

        return std.getId();
    }
}
