package vn.elca.training.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.exception.DeadlineGreaterThanProjectFinishingDateException;

/**
 * @author vlp
 *
 */
@Service
public interface TaskService {
    List<Task> findAll();

	List<Project> findProjectsByTaskName(String taskName);

    List<String> listNumberOfTasks(List<Project> projects);

    List<String> listProjectNameOfRecentTasks();

    List<Task> listTasksById(List<Long> ids);

    Task getTaskById(Long id);

	void updateDeadline(Long taskId, LocalDate deadline) throws DeadlineGreaterThanProjectFinishingDateException;

    void createTaskForProject(String taskName, LocalDate deadline, Project project);
}
