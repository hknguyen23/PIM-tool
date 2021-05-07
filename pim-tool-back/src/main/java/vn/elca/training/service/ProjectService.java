package vn.elca.training.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enumerator.ProjectStatuses;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    List<Project> findAllByProjectName(String projectName);

    Page<Project> findAllWithPagination(int offset, int limit, String status, String searchValue);

    long count();

    long countByStatus(ProjectStatuses status);

    Project findOne(Long id);

    Project findById(Long id);

    Project update(Project project);

    Project save(Project project);

    void deleteById(Long id);

    void deleteListIds(List<Long> ids);

    Boolean checkExistProjectNumber(Long projectNumber);
}
