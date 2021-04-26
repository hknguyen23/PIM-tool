package vn.elca.training.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONObject;
import vn.elca.training.model.exception.NullProjectPropertiesException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gtn
 *
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends Key implements Serializable {
    public static final List<String> columnNames = new ArrayList<>();
    static {
        columnNames.add("username");
        columnNames.add("fullName");
        columnNames.add("password");
    }

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private String fullName;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    public User() {
    }

    public User(JSONObject object) {
        for (String columnName : columnNames) {
            if (object.has(columnName)) {
                if (JSONObject.NULL.equals(object.get(columnName))) {
                    throw new NullProjectPropertiesException(columnName + " has null value");
                }
            }
        }

        this.id = object.has("id") ? object.getLong("id") : null;
        this.fullName = object.has("fullName") ? object.getString("fullName") : null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
