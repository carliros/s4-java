package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.StudentDao;
import s4.dao.SubjectDao;
import s4.model.Student;
import s4.model.Subject;

import java.util.List;
import java.util.Optional;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    public List getSubjects(){
        return subjectDao.getSubjectList();
    }

    public Integer createSubject(Subject subject) {
        return subjectDao.createSubject(subject);
    }

    public Optional<Subject> findSubject(Integer id){
        return subjectDao.findSubject(id);
    }

    public Optional<Integer> deleteSubject(Integer id){
        return subjectDao.removeSubject(id);
    }

    public Optional<Integer> editSubject(Subject subject) {
        return subjectDao.editSubject(subject);
    }
}
