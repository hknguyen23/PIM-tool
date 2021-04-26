package vn.elca.training.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.*;

import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class ApplicationMapper {
    private final ModelMapper mapper = new ModelMapper();

    public ApplicationMapper() {
        // Mapper utility class
    }

    public ProjectDto projectToProjectDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setVersion(project.getVersion());
        dto.setProjectNumber(project.getProjectNumber());
        dto.setProjectName(project.getProjectName());
        dto.setCustomer(project.getCustomer());
        dto.setGroup(project.getGroupz());
        dto.setStatus(project.getStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setEmployees(project.getEmployees().stream().map(this::employeeToEmployeeDto).collect(Collectors.toList()));

//        dto.setFinishingDate(project.getFinishingDate());
//        dto.setActivated(project.getActivated());
//        dto.setTasks(project.getTasks().stream().map(this::taskToTaskDto).collect(Collectors.toList()));
        return dto;
    }

    public TaskDto taskToTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTaskName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setProjectName(task.getProject() != null
                ? task.getProject().getProjectName()
                : null);
        return dto;
    }

    public UserDto userToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setTasks(user.getTasks().stream().map(this::taskToTaskDto).collect(Collectors.toList()));
        return dto;
    }

    public GroupDto groupToGroupDto(Groupz groupz) {
        GroupDto dto = new GroupDto();
        dto.setId(groupz.getId());
        dto.setName(groupz.getName());
        return dto;
    }

    public EmployeeDto employeeToEmployeeDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setVisa(employee.getVisa());
        dto.setFullName(employee.getFirstName(), employee.getLastName());
        dto.setBirthDate(employee.getBirthDate());
        return dto;
    }
}
