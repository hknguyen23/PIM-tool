package vn.elca.training.repository;

import java.time.LocalDate;
import java.util.List;

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
                    finishingDate,
                    String.format("Customer %s", i * 100),
                    true
            );
            projectRepository.save(project);
        }
    }

    public void createGroupAndProjectData(int nGroups, int nProjects) {
        for (int i = 1; i <= nGroups; i++) {
            Groupz groupz = new Groupz(String.format("Group %s", i));
            groupz = groupRepository.save(groupz);
            for (int j = 1; j <= nProjects; j++) {
                LocalDate finishingDate = LocalDate.now().plusYears(1);
                Project project = new Project(
                        String.format("Project %s", (i - 1) * nProjects + j),
                        finishingDate,
                        String.format("Customer %s", i * 100),
                        true
                );
                project.setGroupz(groupz);
                projectRepository.save(project);
            }
        }
    }

    @Test
    public void testCountAll() {
        projectRepository.save(new Project("KSTA", LocalDate.now()));
        projectRepository.save(new Project("LAGAPEO", LocalDate.now()));
        projectRepository.save(new Project("ZHQUEST", LocalDate.now()));
        projectRepository.save(new Project("SECUTIX", LocalDate.now()));
        Assert.assertEquals(15, projectRepository.count());
    }

    @Test
    public void testFindOneWithSimpleQueryDSL() {
        final String PROJECT_NAME = "KSTA";
        projectRepository.save(new Project(PROJECT_NAME, LocalDate.now(), true));
        Project project = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectName.eq(PROJECT_NAME))
                .where(QProject.project.isActivated.eq(true))
                .fetchFirst();
        Assert.assertEquals(PROJECT_NAME, project.getProjectName());
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
        final Boolean PROJECT_ACTIVATED = Boolean.TRUE;

//        List<Project> projects = new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .innerJoin(QProject.project.groupz, QGroupz.groupz)
//                .fetchJoin()
//                .fetch();

        Project projectTest = new JPAQuery<Project>(em)
                .from(QProject.project)
                .innerJoin(QProject.project.groupz, QGroupz.groupz)
                .fetchJoin()
                .where(QProject.project.projectName.eq(PROJECT_NAME)
                        .and(QProject.project.customer.eq(PROJECT_CUSTOMER))
                        .and(QProject.project.isActivated.eq(PROJECT_ACTIVATED))
                        .and(QProject.project.groupz.name.eq(GROUP_NAME)))
                .fetchFirst();

//        List<Project> projects = new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .innerJoin(QProject.project.groupz, QGroupz.groupz)
//                .innerJoin(QProject.project.employees, QEmployee.employee)
//                .fetchJoin()
//                .where(QProject.project.status.eq("NEW")
//                        .and(QProject.project.projectNumber.eq(1L)
//                                .or(QProject.project.projectName.eq("NAME")
//                                .or(QProject.project.customer.eq("CUSTOMER")))
//                        )
//                )
//                .orderBy(QProject.project.projectNumber.asc())
//                .offset(0)
//                .limit(5)
//                .fetch();

        Assert.assertEquals(PROJECT_NAME, projectTest.getProjectName());
        Assert.assertEquals(PROJECT_CUSTOMER, projectTest.getCustomer());
        Assert.assertEquals(PROJECT_ACTIVATED, projectTest.getActivated());
        Assert.assertEquals(GROUP_NAME, projectTest.getGroupz().getName());
    }
}
