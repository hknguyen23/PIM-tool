package vn.elca.training.repository.custom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;

import java.util.List;

/**
 * @author gtn
 *
 */
@Repository
public interface TaskRepositoryCustom {
    List<Project> findProjectsByTaskName(String taskName);

    List<Task> listRecentTasks(int limit);
}
