package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Groupz;
import vn.elca.training.model.exception.EmployeeNotFoundException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.service.EmployeeService;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

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
}
