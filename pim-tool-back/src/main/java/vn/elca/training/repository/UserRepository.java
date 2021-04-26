package vn.elca.training.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.User;

/**
 * @author gtn
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);
}
