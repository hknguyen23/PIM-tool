package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Groupz;

@Repository
public interface GroupRepository extends JpaRepository<Groupz, Long>, QuerydslPredicateExecutor<Groupz> {
}
