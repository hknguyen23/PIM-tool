package vn.elca.training.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Groupz extends Key {
    @Column(name = "GroupName", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_Leader_ID")
    private Employee employee;

    @OneToMany(mappedBy = "groupz", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public Groupz() {
        super();
    }

    public Groupz(String name) {
        this.name = name;
    }

    public Groupz(Map<String, Object> requestBody) {
        this.employee = null;
        requestBody.keySet().forEach(key -> {
            Object object = requestBody.get(key);
            if ("groupName".equals(key)) {
                this.name = (String)object;
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
