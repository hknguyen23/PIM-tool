package vn.elca.training.model.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import vn.elca.training.model.enumerator.ProjectStatuses;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
 *
 */
public class ProjectDto extends AbstractEntityDto {
    @NotNull
    private Long projectNumber;

    @NotBlank
    @Length(max = 50)
    private String projectName;

    @NotBlank
    @Length(max = 50)
    private String customer;

    @NotNull
    private ProjectStatuses status;

    @NotNull
    private GroupDto group;

    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate endDate;

    private List<Long> employeeIds;
    private List<EmployeeDto> employees;

    public Long getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Long projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ProjectStatuses getStatus() {
        return this.status;
    }

    public void setStatus(ProjectStatuses status) {
        this.status = status;
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

}
