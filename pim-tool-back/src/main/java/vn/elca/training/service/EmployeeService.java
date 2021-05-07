package vn.elca.training.service;

import vn.elca.training.model.entity.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee findOne(Long id);

    List<Employee> findAll();

    Set<Employee> findAllByListIds(List<Long> ids);
}
