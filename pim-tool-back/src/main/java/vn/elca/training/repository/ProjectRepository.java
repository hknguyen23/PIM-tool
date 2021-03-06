package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enumerator.ProjectStatuses;

/**
 * @author vlp
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {
    Boolean existsByProjectNumber(Long projectNumber);

    long countByStatus(ProjectStatuses status);
}
