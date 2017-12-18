package s4.services;

import org.springframework.stereotype.Service;
import s4.model.Student;

import java.util.Arrays;
import java.util.List;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class StudentService {
    public List getStudents(){
        Student pedro = new Student();
        pedro.setId(1);
        pedro.setFirstName("Pedro");
        pedro.setLastName("Gonzales");

        List studentList = Arrays.asList(pedro);

        return studentList;
    }
}
