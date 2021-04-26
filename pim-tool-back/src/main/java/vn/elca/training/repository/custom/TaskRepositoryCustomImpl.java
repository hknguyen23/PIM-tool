package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author gtn
 *
 */
// Rename this class so that Spring can scan and wire this component correctly
@Repository
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findProjectsByTaskName(String taskName) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .innerJoin(QProject.project.tasks, QTask.task)
                .fetchJoin()
                .where(QTask.task.name.eq(taskName))
                .fetch();
    }

    @Override
    public List<Task> listRecentTasks(int limit) {
        return new JPAQuery<Task>(em)
                .from(QTask.task)
                .innerJoin(QTask.task.project, QProject.project)
                .fetchJoin()
                .orderBy(QTask.task.id.desc())
                .limit(limit)
                .fetch();
    }
}
