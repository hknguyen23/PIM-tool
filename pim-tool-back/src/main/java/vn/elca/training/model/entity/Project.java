package vn.elca.training.model.entity;

import org.hibernate.validator.constraints.Range;
import vn.elca.training.model.enumerator.ProjectStatuses;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author vlp
 */
@Entity
public class Project extends AbstractEntity {
    @Column(name = "ProjectNumber", nullable = false)
    private Long projectNumber;

    @Column(name = "ProjectName", length = 50, nullable = false)
    private String projectName;

    @Column(name = "Customer", length = 50, nullable = false)
    private String customer;

    @Column(name = "Status", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatuses status;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_ID")
    private Group group;

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

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project(String projectName, String customer) {
        this.projectName = projectName;
        this.customer = customer;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Long projectNumber) {
        this.projectNumber = projectNumber;
    }

    public ProjectStatuses getStatus() {
        return status;
    }

    public void setStatus(ProjectStatuses status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project)o;
        return id.equals(project.id) && projectNumber.equals(project.projectNumber)
                && projectName.equals(project.projectName) && customer.equals(project.customer)
                && status == project.status && startDate.equals(project.startDate)
                && Objects.equals(endDate, project.endDate) && group.equals(project.group)
                && Objects.equals(employees, project.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectNumber, projectName, customer, status, startDate, endDate, group, employees);
    }
}