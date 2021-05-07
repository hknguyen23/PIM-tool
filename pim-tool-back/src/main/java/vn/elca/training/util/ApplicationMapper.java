package vn.elca.training.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.*;
import vn.elca.training.model.enumerator.ProjectStatuses;

import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class ApplicationMapper {
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
        dto.setGroup(groupToGroupDto(project.getGroup()));
        dto.setStatus(project.getStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setEmployees(project.getEmployees().stream().map(this::employeeToEmployeeDto).collect(Collectors.toList()));

        return dto;
    }

    public Project projectDtoToProject(ProjectDto dto) {
        Project project = new Project();

        project.setId(dto.getId());
        project.setVersion(dto.getVersion());
        project.setProjectNumber(dto.getProjectNumber());
        project.setProjectName(dto.getProjectName());
        project.setCustomer(dto.getCustomer());
        project.setStatus(dto.getStatus());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());

        return project;
    }

    public Project projectToProject(Project oldProject) {
        Project newProject = new Project();

        newProject.setId(oldProject.getId());
        newProject.setVersion(oldProject.getVersion());
        newProject.setProjectNumber(oldProject.getProjectNumber());
        newProject.setProjectName(oldProject.getProjectName());
        newProject.setCustomer(oldProject.getCustomer());
        newProject.setStatus(oldProject.getStatus());
        newProject.setStartDate(oldProject.getStartDate());
        newProject.setEndDate(oldProject.getEndDate());
        newProject.setGroup(oldProject.getGroup());
        newProject.setEmployees(oldProject.getEmployees());

        return newProject;
    }

    public GroupDto groupToGroupDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
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
