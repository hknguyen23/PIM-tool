package vn.elca.training.model.dto;

import vn.elca.training.model.entity.Groupz;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private final String VALUE_ATTRIBUTE = "value";
    private final String TITLE_ATTRIBUTE = "title";

    private Long id;
    private Long version;
    private Long projectNumber;
    private String projectName;
    private String customer;
    private Map<String, Object> status = new LinkedHashMap<>();
    private Map<String, Object> group = new LinkedHashMap<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> employeeIds;
    private List<EmployeeDto> employees;

    private List<TaskDto> tasks;
    private LocalDate finishingDate;
    private Boolean isActivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

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

    public Map<String, Object> getStatus() {
        return status;
    }

    public void setStatus(String status) {
        switch (status) {
            case "NEW":
                this.status.put(VALUE_ATTRIBUTE, status);
                this.status.put(TITLE_ATTRIBUTE, "New");
                break;
            case "PLA":
                this.status.put(VALUE_ATTRIBUTE, status);
                this.status.put(TITLE_ATTRIBUTE, "Planned");
                break;
            case "INP":
                this.status.put(VALUE_ATTRIBUTE, status);
                this.status.put(TITLE_ATTRIBUTE, "In process");
                break;
            case "FIN":
                this.status.put(VALUE_ATTRIBUTE, status);
                this.status.put(TITLE_ATTRIBUTE, "Finished");
                break;
            default:
                this.status.put(VALUE_ATTRIBUTE, null);
                this.status.put(TITLE_ATTRIBUTE, null);
        }
    }

    public Map<String, Object> getGroup() {
        return group;
    }

    public void setGroup(Groupz groupz) {
        this.group.put("id", groupz.getId());
        this.group.put("name", groupz.getName());
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

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
