package vn.elca.training.repository;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.*;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value=SpringRunner.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GroupRepository groupRepository;

    public void createProjectData(int nProjects) {
        for (int i = 1; i <= nProjects; i++) {
            LocalDate finishingDate = LocalDate.now().plusYears(1);
            Project project = new Project(
                    String.format("Project %s", i),
                    String.format("Customer %s", i * 100)
            );
            projectRepository.save(project);
        }
    }

    public void createGroupAndProjectData(int nGroups, int nProjects) {
        for (int i = 1; i <= nGroups; i++) {
            Group group = new Group(String.format("Group %s", i));
            group = groupRepository.save(group);
            for (int j = 1; j <= nProjects; j++) {
                Project project = new Project(
                        String.format("Project %s", (i - 1) * nProjects + j),
                        String.format("Customer %s", i * 100)
                );
                project.setGroup(group);
                projectRepository.save(project);
            }
        }
    }

    @Test
    public void testDeleteProject() {
        createProjectData(5);
        Long idToDelete = 1L;
        projectRepository.deleteById(idToDelete);
        Assert.assertEquals(15, projectRepository.count());
    }

    @Test
    public void testFindOneWithComplexQueryDSL() {
        createGroupAndProjectData(3, 5);
        final String GROUP_NAME = "Group 2";
        final String PROJECT_NAME = "Project 8";
        final String PROJECT_CUSTOMER = "Customer 200";

        Project projectTest = new JPAQuery<Project>(em)
                .from(QProject.project)
                .innerJoin(QProject.project.group, QGroup.group)
                .fetchJoin()
                .where(QProject.project.projectName.eq(PROJECT_NAME)
                        .and(QProject.project.customer.eq(PROJECT_CUSTOMER))
                        .and(QProject.project.group.name.eq(GROUP_NAME)))
                .fetchFirst();

        Assert.assertEquals(PROJECT_NAME, projectTest.getProjectName());
        Assert.assertEquals(PROJECT_CUSTOMER, projectTest.getCustomer());
        Assert.assertEquals(GROUP_NAME, projectTest.getGroup().getName());
    }
}
