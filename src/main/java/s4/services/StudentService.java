package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.StudentDao;
import s4.model.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public List getStudents(){
        return studentDao.getStudentList();
    }

    public Integer createStudent(String firstName, String lastName) {
        return studentDao.createStudent(firstName, lastName);
    }

    public Optional<Student> findStudent(Integer id){
        return studentDao.findStudent(id);
    }

    public Integer deleteStudent(Student std){
        return studentDao.removeStudent(std.getId());
    }

    public Integer editStudent(Student std) {
        return studentDao.editStudent(std);
    }

    public List<Student> searchByFirstName(String firstName) {
        return studentDao.getStudentList().stream()
                .filter(student -> student.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public List<Student> searchByLastName(String lastName) {
        return studentDao.getStudentList().stream()
                .filter(student -> student.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }
}
