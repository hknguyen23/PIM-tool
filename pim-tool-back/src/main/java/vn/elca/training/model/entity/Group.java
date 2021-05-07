package vn.elca.training.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Groupz" )
public class Group extends AbstractEntity {
    @Column(name = "GroupName", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Group_Leader_ID")
    private Employee leader;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public Group() {
        super();
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
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

        Group group = (Group)o;
        return id.equals(group.id) && name.equals(group.name)
                && leader.equals(group.leader) && projects.equals(group.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
