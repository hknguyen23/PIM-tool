package vn.elca.training.model.entity;

import org.apache.tomcat.jni.Local;
import org.json.JSONObject;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.exception.InvalidProjectFinishingDateFormatException;
import vn.elca.training.model.exception.NullProjectPropertiesException;
import vn.elca.training.util.DateTimeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * @author vlp
 */
@Entity
public class Project extends Key {
    public static final List<String> columnNames = new ArrayList<>();
    static {
        columnNames.add("name");
        columnNames.add("customer");
        columnNames.add("finishingDate");
    }

    @Column(name = "ProjectNumber", nullable = false)
    private Long projectNumber;

    @Column(name = "ProjectName", length = 50, nullable = false)
    private String projectName;

    @Column(name = "Customer", length = 50, nullable = false)
    private String customer;

    @Column(name = "Status", length = 3, nullable = false)
    private String status;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "LastModifiedDate")
    private LocalDate lastModifiedDate;

    @Column
    private LocalDate finishingDate;

    @Column
    private Boolean isActivated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_ID")
    private Groupz groupz;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ProjectEmployee",
            joinColumns = { @JoinColumn(name = "Project_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Employee_ID") }
    )
    private Set<Employee> employees = new HashSet<>();

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Project() {
    }

    public Project(ProjectDto dto) {
        this.id = dto.getId();
        this.projectNumber = dto.getProjectNumber();
        this.projectName = dto.getProjectName();
        this.customer = dto.getCustomer();
        this.status = (String)dto.getStatus().get("value");
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.lastModifiedDate = LocalDate.now();
    }

    public Project(String projectName, LocalDate finishingDate) {
        this.projectName = projectName;
        this.finishingDate = finishingDate;
    }

    public Project(String projectName, LocalDate finishingDate, Boolean isActivated) {
        this.projectName = projectName;
        this.finishingDate = finishingDate;
        this.isActivated = isActivated;
    }

    public Project(String projectName, LocalDate finishingDate, String customer, Boolean isActivated) {
        this.projectName = projectName;
        this.finishingDate = finishingDate;
        this.customer = customer;
        this.isActivated = isActivated;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Groupz getGroupz() {
        return groupz;
    }

    public void setGroupz(Groupz groupz) {
        this.groupz = groupz;
    }

    public Long getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Long projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}