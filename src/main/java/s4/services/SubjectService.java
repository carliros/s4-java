package s4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4.dao.SubjectDao;
import s4.dao.SubjectStudentDao;
import s4.model.Subject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by carlos on 12/18/17.
 */

@Service
public class SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SubjectStudentDao subjectStudentDao;

    public List<Subject> getSubjects(Optional<String> code, Optional<String> title, Optional<String> description, Optional<Integer> studentId) {
        Stream<Subject> classStream;

        if (studentId.isPresent()) {
            classStream = subjectStudentDao.getClassesForStudent(studentId.get()).stream();
        }
        else {
            classStream = subjectDao.getSubjectList().stream();
        }

        return classStream
                .filter(subject -> filterClass(subject, code, title, description))
                .collect(Collectors.toList());
    }

    private boolean filterClass(Subject subject, Optional<String> code, Optional<String> title, Optional<String> description) {
        boolean result = true;

        if (code.isPresent() && title.isPresent() && description.isPresent()) {
            result = subject.getCode().contains(code.get())
                        || subject.getTitle().contains(title.get())
                        || subject.getDescription().contains(description.get());
        }
        else if (code.isPresent() && title.isPresent()) {
            result = subject.getCode().contains(code.get())
                        || subject.getTitle().contains(title.get());
        }
        else if (code.isPresent() && description.isPresent()) {
            result = subject.getCode().contains(code.get())
                        || subject.getDescription().contains(description.get());
        }
        else if (title.isPresent() && description.isPresent()) {
            result = subject.getTitle().contains(title.get())
                        || subject.getDescription().contains(description.get());
        }
        else if (code.isPresent()) {
            result = subject.getCode().contains(code.get());
        }
        else if (title.isPresent()) {
            result = subject.getTitle().contains(title.get());
        }
        else if (description.isPresent()) {
            result = subject.getDescription().contains(description.get());
        }

        return result;
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
}
