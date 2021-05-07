package vn.elca.training.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enumerator.ProjectStatuses;
import vn.elca.training.model.exception.DeleteProjectException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.exception.ProjectVersionNotMatchedException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.custom.ProjectRepositoryCustom;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 *
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRepositoryCustom projectRepositoryCustom;

    @Autowired
    ApplicationMapper mapper;

    @Override
    @Transactional
    public List<Project> findAllByProjectName(String projectName) {
        return projectRepositoryCustom.findAllByProjectName(projectName);
    }

    @Override
    public Page<Project> findAllWithPagination(int offset, int limit, String status, String searchValue) {
        List<Project> projects;

        try {
            projects = projectRepositoryCustom.findAllWithCriterion(
                    status,
                    Long.parseLong(searchValue),
                    searchValue,
                    searchValue,
                    offset,
                    limit
            );
        } catch (NumberFormatException ex) {
            projects = projectRepositoryCustom.findAllWithCriterion(
                    status,
                    null,
                    searchValue,
                    searchValue,
                    offset,
                    limit
            );
        }

        return new PageImpl<>(projects);
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public long countByStatus(ProjectStatuses status) {
        return projectRepository.countByStatus(status);
    }

    @Override
    public Project findOne(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        return project;
    }

    @Override
    public Project findById(Long id) {
        // with fetch group and employee
        Project project = projectRepositoryCustom.findById(id);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        return project;
    }

    @Override
    public Project update(Project project) {
        try {
            Project toBeUpdatedProject = mapper.projectToProject(project); // create a new project from an existing project
            return projectRepository.save(toBeUpdatedProject);
        } catch (Exception ex) {
            throw new ProjectVersionNotMatchedException("Project's version is not matched");
        }
    }

    @Override
    public Project save(Project project) {
        boolean isExistProjectNumber = projectRepository.existsByProjectNumber(project.getProjectNumber());

        if (isExistProjectNumber) {
            throw new ProjectNumberAlreadyExistsException("The project number already existed. Please select a different project number");
        }

        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        Project toBeDeletedProject = findOne(id);
        if (toBeDeletedProject == null) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }

        if (toBeDeletedProject.getStatus().equals(ProjectStatuses.NEW)) {
            projectRepository.deleteById(id);
        } else {
            throw new DeleteProjectException("Only project with status \"NEW\" can be deleted");
        }
    }

    @Override
    public void deleteListIds(List<Long> ids) throws ProjectNotFoundException, DeleteProjectException {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Boolean checkExistProjectNumber(Long projectNumber) {
        return projectRepository.existsByProjectNumber(projectNumber);
    }
}
