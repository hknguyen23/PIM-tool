package vn.elca.training.service.impl;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Groupz;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.ProjectVersionNotMatched;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 *
 */
@Service
@Transactional
@Primary
@Profile("!dummy | dev")
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> findAllWithPagination(int page, int size, String status, String searchValue) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "projectNumber");
        if (!"".equals(status) && !"".equals(searchValue)) {
            try {
                return projectRepository.findByStatusAndProjectNumberOrStatusAndProjectNameIsContainingIgnoreCaseOrStatusAndCustomerIsContainingIgnoreCase(
                        status,
                        Long.parseLong(searchValue),
                        status,
                        searchValue,
                        status,
                        searchValue,
                        pageable
                );
            } catch (NumberFormatException ex) {
                return projectRepository.findByStatusAndProjectNameIsContainingIgnoreCaseOrStatusAndCustomerIsContainingIgnoreCase(
                        status,
                        searchValue,
                        status,
                        searchValue,
                        pageable
                );
            }
        }

        if (!"".equals(status)) {
            return projectRepository.findByStatus(status, pageable);
        }

        if (!"".equals(searchValue)) {
            try {
                return projectRepository.findByProjectNumberOrProjectNameIsContainingIgnoreCaseOrCustomerIsContainingIgnoreCase(
                        Long.parseLong(searchValue),
                        searchValue,
                        searchValue,
                        pageable
                );
            } catch (NumberFormatException ex) {
                return projectRepository.findByProjectNameIsContainingIgnoreCaseOrCustomerIsContainingIgnoreCase(
                        searchValue,
                        searchValue,
                        pageable
                );
            }
        }

        return projectRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Project findOne(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found!!!");
        }
        return project;
    }

    @Override
    public Project update(Project project, Long version) {
        Project tobeUpdatedProject = findOne(project.getId());
        if (tobeUpdatedProject == null) {
            throw new ProjectNotFoundException("Project not found");
        }

        if (!version.equals(tobeUpdatedProject.getVersion())) {
            throw new ProjectVersionNotMatched("Project's version is not matched");
        }

        Long projectNumber = project.getProjectNumber();
        if (projectNumber != null) {
            tobeUpdatedProject.setProjectNumber(projectNumber);
        }

        String name = project.getProjectName();
        if (name != null) {
            tobeUpdatedProject.setProjectName(name);
        }

        String customer = project.getCustomer();
        if (customer != null) {
            tobeUpdatedProject.setCustomer(customer);
        }

        String status = project.getStatus();
        if (status != null) {
            tobeUpdatedProject.setStatus(status);
        }

        LocalDate startDate = project.getStartDate();
        if (startDate != null) {
            tobeUpdatedProject.setStartDate(startDate);
        }

        LocalDate endDate = project.getEndDate();
        if (endDate != null) {
            tobeUpdatedProject.setEndDate(endDate);
        }

        LocalDate lastModifiedDate = project.getLastModifiedDate();
        if (lastModifiedDate != null) {
            tobeUpdatedProject.setLastModifiedDate(lastModifiedDate);
        }

        Groupz groupz = project.getGroupz();
        if (groupz != null) {
            tobeUpdatedProject.setGroupz(groupz);
        }

        tobeUpdatedProject.setEmployees(project.getEmployees());

        LocalDate finishingDate = project.getFinishingDate();
        if (finishingDate != null) {
            tobeUpdatedProject.setFinishingDate(finishingDate);
        }

        Boolean isActivated = project.getActivated();
        if (isActivated != null) {
            tobeUpdatedProject.setActivated(isActivated);
        }

        return projectRepository.save(tobeUpdatedProject);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        Project tobeUpdatedProject = findOne(id);
        if (tobeUpdatedProject == null) {
            throw new ProjectNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public Project createMaintenanceProject(Project oldProject) {
        Project newProject = new Project();
        StringBuilder sb = new StringBuilder();
        sb.append(oldProject.getProjectName()).append("Maint.").append(LocalDate.now().getYear());
        newProject.setProjectName(sb.toString());
        newProject.setCustomer(oldProject.getCustomer());
        newProject.setActivated(true);
        newProject.setFinishingDate(oldProject.getFinishingDate());
        newProject.setTasks(oldProject.getTasks());

        return projectRepository.save(newProject);
    }

    @Override
    public Boolean checkExistProjectNumber(Long projectNumber) {
        return projectRepository.existsByProjectNumber(projectNumber);
    }
}
