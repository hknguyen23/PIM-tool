package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.TaskDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController extends AbstractApplicationController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public List<TaskDto> findAll() {
        return taskService.findAll()
                .stream()
                .map(mapper::taskToTaskDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/listNumberOfTasks")
    public List<String> listNumberOfTask(@RequestBody List<Project> projects) {
        return taskService.listNumberOfTasks(projects);
    }
}
