package vn.elca.training.repository.custom;

import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Employee;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepositoryCustom {
    List<Employee> findAllByListIds(List<Long> ids);
}
