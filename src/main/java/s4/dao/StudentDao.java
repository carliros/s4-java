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
import java.util.stream.Collectors;

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
        return studentList.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    public Integer removeStudent(Integer id) {
        studentList.removeIf(student -> {
            boolean doesMatchId = student.getId().equals(id);
            if (doesMatchId) {
                log.info("Student removed.");
            }

            return doesMatchId;
        });

        return id;
    }

    public Integer editStudent(Student newStudent) {
        studentList.stream().forEach(oldStudent -> {
            if (oldStudent.getId().equals(newStudent.getId())) {
                oldStudent.setFirstName(newStudent.getFirstName());
                oldStudent.setLastName(newStudent.getLastName());
                log.info("Student edited.");
            }
        });

        return newStudent.getId();
    }
}
