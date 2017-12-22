package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.StudentDao;
import s4.dao.SubjectStudentDao;
import s4.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SubjectStudentDao subjectStudentDao;

    public List<Student> getStudents(Optional<String> firstName, Optional<String> lastName, Optional<Integer> classId) {
        Stream<Student> studentStream;

        if (classId.isPresent()){
            studentStream = subjectStudentDao.getStudentsForClass(classId.get()).stream();
        }
        else {
            studentStream = studentDao.getStudentList().stream();
        }

        return studentStream
                .filter(student -> filterStudent(student, firstName, lastName))
                .collect(Collectors.toList());
    }

    private boolean filterStudent(Student student, Optional<String> firstName, Optional<String> lastName) {
        boolean result = true;
        if (firstName.isPresent() && lastName.isPresent())
        {
            result = student.getFirstName().contains(firstName.get())
                    || student.getLastName().contains(lastName.get());
        }
        else if (firstName.isPresent()) {
            result = student.getFirstName().contains(firstName.get());
        }
        else if (lastName.isPresent()) {
            result = student.getLastName().contains(lastName.get());
        }

        return result;
    }

    public Integer createStudent(String firstName, String lastName) {
        return studentDao.createStudent(firstName, lastName);
    }

    public Optional<Student> findStudent(Integer id) {
        return studentDao.findStudent(id);
    }

    public Integer deleteStudent(Student std) {
        return studentDao.removeStudent(std.getId());
    }

    public Integer editStudent(Student std) {
        return studentDao.editStudent(std);
    }
}
