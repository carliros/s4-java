package s4.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s4.model.Student;
import s4.model.Subject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by carlos on 12/18/17.
 */
@Component
public class SubjectStudentDao {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SubjectDao subjectDao;

    private Map<Integer, Set<Integer>> subjectStudentMap = new HashMap<>();

    public void registerStudent(Integer subjectId, Integer studentId) {
        Set<Integer> studentList;
        if (subjectStudentMap.containsKey(subjectId)) {
            studentList = subjectStudentMap.get(subjectId);
        } else {
            studentList = new HashSet<Integer>();
        }

        studentList.add(studentId);

        subjectStudentMap.put(subjectId, studentList);
    }

    public List<Student> getStudentsForClass(Integer subjectId) {
        List<Student> studentList = new ArrayList<>();

        if (subjectStudentMap.containsKey(subjectId)){
            Set<Integer> subjectIdSet = subjectStudentMap.get(subjectId);

            studentList = subjectIdSet.stream()
                    .map(studentId -> studentDao.findStudent(studentId))
                    .filter(optional -> optional.isPresent())
                    .map(optional -> optional.get())
                    .collect(Collectors.toList());
        }

        return studentList;
    }

    public List<Subject> getClassesForStudent(Integer studentId){
        List<Subject> subjectList = subjectStudentMap.entrySet().stream()
                .filter(entry -> entry.getValue().contains(studentId))
                .map(entry -> subjectDao.findSubject(entry.getKey()))
                .filter(optional -> optional.isPresent())
                .map(optional -> optional.get())
                .collect(Collectors.toList());

        return subjectList;
    }
}
