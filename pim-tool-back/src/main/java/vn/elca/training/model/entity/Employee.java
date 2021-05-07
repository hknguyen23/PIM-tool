package vn.elca.training.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Employee extends AbstractEntity {
    @Column(name = "Visa", length = 3, nullable = false)
    private String visa;

    @Column(name = "FirstName", length = 50, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "BirthDate", nullable = false)
    private LocalDate birthDate;

    @OneToOne(mappedBy = "leader", fetch = FetchType.LAZY)
    private Group group;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public Employee() {
        super();
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee)o;
        return id.equals(employee.id) && visa.equals(employee.visa) && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName) && birthDate.equals(employee.birthDate)
                && Objects.equals(group, employee.group) && Objects.equals(projects, employee.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visa, firstName, lastName, birthDate);
    }
}
