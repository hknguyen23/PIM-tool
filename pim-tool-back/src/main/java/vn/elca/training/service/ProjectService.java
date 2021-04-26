package vn.elca.training.service;

import java.util.List;

import org.springframework.data.domain.Page;
import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    List<Project> findAll();

    Page<Project> findAllWithPagination(int page, int size, String status, String searchValue);

    long count();

    Project findOne(Long id);

    Project update(Project project, Long version);

    Project save(Project project);

    void deleteById(Long id);

    Project createMaintenanceProject(Project oldProject);

    Boolean checkExistProjectNumber(Long projectNumber);
}
