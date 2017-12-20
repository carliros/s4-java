package s4.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import s4.model.Subject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by carlos on 12/18/17.
 */
@Component
public class SubjectDao {
    //mocking persistence layer here

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger idGenerator = new AtomicInteger(0);
    private List<Subject> subjectList = new ArrayList<>();

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public Integer createSubject(Subject subject) {
        subject.setId(idGenerator.incrementAndGet());

        subjectList.add(subject);

        return subject.getId();
    }

    public Optional<Subject> findSubject(Integer id) {
        for (Subject sub : subjectList) {
            if (sub.getId().equals(id)) {
                return Optional.of(sub);
            }
        }

        return Optional.empty();
    }

    public Optional<Integer> removeSubject(Integer id) {
        for (int index = 0; index < subjectList.size(); index++) {
            Subject sub = subjectList.get(index);
            if (sub.getId().equals(id)) {
                subjectList.remove(index);
                log.info("Subject removed.");

                return Optional.of(id);
            }
        }

        return Optional.empty();
    }

    public Optional<Integer> editSubject(Subject sub) {
        for (int index = 0; index < subjectList.size(); index++) {
            Subject old = subjectList.get(index);
            if (old.getId().equals(sub.getId())) {
                old.setCode(sub.getCode());
                old.setTitle(sub.getTitle());
                old.setDescription(sub.getDescription());

                log.info("Subject edited.");
                return Optional.of(old.getId());
            }
        }

        return Optional.empty();
    }
}
