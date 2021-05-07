package vn.elca.training.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QEmployee;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.enumerator.ProjectStatuses;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAllByProjectName(String projectName) {
        QProject project = QProject.project;

        return new JPAQuery<Project>(em)
                .from(project)
                .innerJoin(project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(project.employees, QEmployee.employee)
                .fetchJoin()
                .where(project.projectName.stringValue().containsIgnoreCase(projectName))
                .orderBy(project.projectNumber.asc())
                .fetch();
    }

    @Override
    public List<Project> findAllWithCriterion(
            String status,
            Long projectNumber,
            String projectName,
            String customer,
            int offset,
            int limit
    ) {
        QProject project = QProject.project;
        BooleanBuilder conditions = new BooleanBuilder();

        if (!StringUtils.isEmpty(status)) {
            conditions.and(project.status.eq(ProjectStatuses.valueOf(status)));
        }

        BooleanBuilder subBuilder = new BooleanBuilder();
        if (projectNumber != null) {
            subBuilder.or(project.projectNumber.eq(projectNumber));
        }

        if (!StringUtils.isEmpty(projectName)) {
            subBuilder.or(project.projectName.stringValue().containsIgnoreCase(projectName));
        }

        if (!StringUtils.isEmpty(customer)) {
            subBuilder.or(project.customer.stringValue().containsIgnoreCase(customer));
        }

        conditions.and(subBuilder);

        return new JPAQuery<Project>(em)
                .from(project)
                .innerJoin(project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(project.employees, QEmployee.employee)
                .fetchJoin()
                .where(conditions)
                .offset(offset)
                .limit(limit)
                .orderBy(project.projectNumber.asc())
                .fetch();
    }

    @Override
    public Project findById(Long id) {
        QProject project = QProject.project;
        return new JPAQuery<Project>(em)
                .from(project)
                .innerJoin(project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(project.employees, QEmployee.employee)
                .fetchJoin()
                .where(project.id.eq(id))
                .fetchOne();
    }
}
