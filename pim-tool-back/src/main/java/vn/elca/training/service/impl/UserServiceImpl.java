package vn.elca.training.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.entity.User;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.model.exception.TaskNotFoundException;
import vn.elca.training.model.exception.UserNotFoundException;
import vn.elca.training.repository.TaskRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<User> findAll() {
        //System.out.println(passwordEncoder.encode("123456"));
        return userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found!!!");
        }
        return user;
    }

    @Override
    public User findOne(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found!!!");
        }
        return user;
    }

    @Override
    public User addTasksToUser(List<Long> taskIds, String username) {
        List<Task> tasks = taskRepository.findAllById(taskIds);
        if (tasks.isEmpty() || tasks.size() != taskIds.size()) {
            throw new TaskNotFoundException("Task not found");
        }

        User user = findOne(username);

        // update data (userID) in Task table
        tasks.forEach(task -> task.setUser(user));
        taskRepository.saveAll(tasks);
        user.setTasks(tasks);

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User tobeUpdatedUser = findOne(user.getId());
        if (tobeUpdatedUser == null) {
            throw new UserNotFoundException("User not found");
        }

        String username = user.getUsername();
        if (username != null) {
            tobeUpdatedUser.setUsername(username);
        }

        String fullName = user.getFullName();
        if (fullName != null) {
            tobeUpdatedUser.setFullName(fullName);
        }

        return userRepository.save(tobeUpdatedUser);
    }
}
