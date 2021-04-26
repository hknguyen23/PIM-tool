package vn.elca.training.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.Project;

import java.util.List;

/**
 * @author vlp
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {
    @Override
    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.groupz LEFT JOIN FETCH p.employees ORDER BY p.lastModifiedDate DESC")
    List<Project> findAll();

    @Override
    Page<Project> findAll(Pageable pageable);

    Page<Project> findByStatus(String status, Pageable pageable);

    /*
        SELECT *
        FROM Project P
        WHERE P.Status = :status
            AND(P.ProjectNumber = :projectNumber OR P.ProjectName = :projectName OR P.Customer = :customer)

        A and (B or C or D) => (A and B) or (A and C) or (A and D)

        findByStatusAndProjectNumberOrStatusAndProjectNameIsContainingIgnoreCaseOrStatusAndCustomerIsContainingIgnoreCase
     */

    Page<Project> findByStatusAndProjectNumberOrStatusAndProjectNameIsContainingIgnoreCaseOrStatusAndCustomerIsContainingIgnoreCase(
            String status1,
            Long projectNumber,
            String status2,
            String projectName,
            String status3,
            String customer,
            Pageable pageable
    );

    Page<Project> findByStatusAndProjectNameIsContainingIgnoreCaseOrStatusAndCustomerIsContainingIgnoreCase(
            String status1,
            String projectName,
            String status2,
            String customer,
            Pageable pageable
    );

    Page<Project> findByProjectNumberOrProjectNameIsContainingIgnoreCaseOrCustomerIsContainingIgnoreCase(
            Long projectNumber,
            String projectName,
            String customer,
            Pageable pageable
    );

    Page<Project> findByProjectNameIsContainingIgnoreCaseOrCustomerIsContainingIgnoreCase(
            String projectName,
            String customer,
            Pageable pageable
    );

    Boolean existsByProjectNumber(Long projectNumber);
}
