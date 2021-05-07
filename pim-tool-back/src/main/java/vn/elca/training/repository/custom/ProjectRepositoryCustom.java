package vn.elca.training.repository.custom;

import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;

import java.util.List;

@Repository
public interface  ProjectRepositoryCustom {
    List<Project> findAllByProjectName(String projectName);

    List<Project> findAllWithCriterion(
            String status,
            Long projectNumber,
            String projectName,
            String customer,
            int offset,
            int limit
    );

    Project findById(Long id);
}
