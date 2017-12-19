package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.SubjectDao;
import s4.dao.SubjectStudentDao;
import s4.model.Subject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SubjectStudentDao subjectStudentDao;

    public List getSubjects() {
        return subjectDao.getSubjectList();
    }

    public Integer createSubject(Subject subject) {
        return subjectDao.createSubject(subject);
    }

    public Optional<Subject> findSubject(Integer id) {
        return subjectDao.findSubject(id);
    }

    public Optional<Integer> deleteSubject(Integer id) {
        return subjectDao.removeSubject(id);
    }

    public Optional<Integer> editSubject(Subject subject) {
        return subjectDao.editSubject(subject);
    }

    public void registerStudent(Integer subjectId, Integer studentId) {
        subjectStudentDao.registerStudent(subjectId, studentId);
    }

    public List<Subject> searchByCode(String code) {
        return subjectDao.getSubjectList().stream()
                .filter(subject -> subject.getCode().equals(code))
                .collect(Collectors.toList());
    }

    public List<Subject> searchByTitle(String title) {
        return subjectDao.getSubjectList().stream()
                .filter(subject -> subject.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public List<Subject> searchByDescription(String description) {
        return subjectDao.getSubjectList().stream()
                .filter(subject -> subject.getDescription().equals(description))
                .collect(Collectors.toList());
    }
}
