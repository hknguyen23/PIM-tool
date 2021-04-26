package vn.elca.training.service;

import vn.elca.training.model.dto.UserDto;
import vn.elca.training.model.entity.User;
import vn.elca.training.model.exception.UserNotFoundException;

import java.util.List;

/**
 * @author gtn
 *
 */
public interface UserService {
    List<User> findAll();

    User findOne(Long id);

    User findOne(String username);

    User addTasksToUser(List<Long> taskIds, String username);

    User update(User user);
}
