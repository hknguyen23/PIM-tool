package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.RestResponse;
import vn.elca.training.model.dto.RestResponseSuccess;
import vn.elca.training.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController extends AbstractApplicationController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public RestResponse findAll() {
        List<EmployeeDto> employeeDtos = employeeService.findAll()
                .stream()
                .map(mapper::employeeToEmployeeDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeDtos);
        return new RestResponseSuccess(
                map,
                "Total employees: " + employeeDtos.size(),
                HttpStatus.OK,
                HttpStatus.OK.value()
        );
    }
}
