package vn.elca.training.service;

import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Groupz;

import java.util.List;

public interface EmployeeService {
    Employee findOne(Long id);

    List<Employee> findAll();
}
