package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.SubjectStudentDao;
import s4.model.Student;
import s4.model.Subject;

import java.util.List;

/**
 * Created by carlos on 12/18/17.
 */
@Service
public class SubjectStudentService {
    @Autowired
    private SubjectStudentDao subjectStudentDao;

    public List<Student> getStudentsForClass(Integer subjectId) {
        return subjectStudentDao.getStudentsForClass(subjectId);
    }

    public List<Subject> getSubjectsForStudent(Integer studentId) {
        return subjectStudentDao.getClassesForStudent(studentId);
    }
}
