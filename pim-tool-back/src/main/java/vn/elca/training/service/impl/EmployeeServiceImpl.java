package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.exception.EmployeeNotFoundException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.custom.EmployeeRepositoryCustom;
import vn.elca.training.repository.custom.EmployeeRepositoryCustomImpl;
import vn.elca.training.service.EmployeeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRepositoryCustom employeeRepositoryCustom;

    @Override
    public Employee findOne(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with " + id + " not found!!!");
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Set<Employee> findAllByListIds(List<Long> ids) {
        List<Employee> employeeList = employeeRepositoryCustom.findAllByListIds(ids);
        return new HashSet<>(employeeList);
    }
}
