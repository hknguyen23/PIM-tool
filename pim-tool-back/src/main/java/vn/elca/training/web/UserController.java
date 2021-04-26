package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.User;
import vn.elca.training.model.exception.*;
import vn.elca.training.service.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractApplicationController {
    //private final String numberRegex = "\\d+";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public RestResponse findAll() {
        List<UserDto> userDtos = userService.findAll()
                .stream()
                .map(mapper::userToUserDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("data", userDtos);
        return new RestResponseSuccess(
                map,
                "Total users: " + userDtos.size(),
                HttpStatus.OK,
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/id/{idString}")
    public RestResponse findById(@PathVariable String idString) {
        Long id = null;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            return new RestResponseFail(
                    "Invalid id: " + idString,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errors
            );
        }

        try {
            User user = userService.findOne(id);
            Map<String, Object> map = new HashMap<>();
            map.put("data", mapper.userToUserDto(user));
            return new RestResponseSuccess(
                    map,
                    "User found",
                    HttpStatus.OK,
                    HttpStatus.OK.value()
            );
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            return new RestResponseFail(
                    "Can't find user with id: " + id,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errors
            );
        }
    }

    @GetMapping("/{username}")
    public RestResponse findByUsername(@PathVariable String username) {
        try {
            User user = userService.findOne(username);
            Map<String, Object> map = new HashMap<>();
            map.put("data", mapper.userToUserDto(user));
            return new RestResponseSuccess(
                    map,
                    "User found",
                    HttpStatus.OK,
                    HttpStatus.OK.value()
            );
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            return new RestResponseFail(
                    "Can't find user with username: " + username,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errors
            );
        }
    }

    @PostMapping("/{username}/addTasks")
    public RestResponse addTasks(@RequestBody String requestBody, @PathVariable String username) {
        JSONArray taskIdArray = null;
        List<Long> taskIds = new ArrayList<>();
        if (requestBody.contains("{")) {
            JSONObject jsonObject = new JSONObject(requestBody);

            if (!jsonObject.has("taskIds")) {
                throw new IllegalArgumentException("Invalid request! taskIds is missing");
            } else if (jsonObject.getJSONArray("taskIds").length() == 0) {
                throw new IllegalArgumentException("Invalid request! List taskIds is empty");
            } else if (StringUtils.isBlank(username)) {
                throw new IllegalArgumentException("Invalid request! Username is blank");
            }

            taskIdArray = jsonObject.getJSONArray("taskIds");
        } else {
            taskIdArray = new JSONArray(requestBody);
        }

        List<String> errors = new ArrayList<>();
        for (int i = 0; i < taskIdArray.length(); i++) {
            Object item = taskIdArray.get(i);
            if (!(item instanceof Number)) {
                errors.add("Invalid task id: " + item);
            } else {
                taskIds.add(taskIdArray.getLong(i));
            }
        }

        if (!errors.isEmpty()) {
            return new RestResponseFail(
                    "Task id must be a number",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errors
            );
        }

        try {
            User user = userService.addTasksToUser(taskIds, username);

            Map<String, Object> map = new HashMap<>();
            map.put("data", mapper.userToUserDto(userService.findOne(user.getId())));
            return new RestResponseSuccess(
                    map,
                    "Add tasks successfully",
                    HttpStatus.OK,
                    HttpStatus.OK.value()
            );

        } catch (TaskNotFoundException ex) {
            ex.printStackTrace();
            errors.add(ex.getMessage());
            return new RestResponseFail(
                    "Invalid task id",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errors
            );
        }
    }

    @PutMapping({"/update"})
    @ResponseBody
    public RestResponse update(@RequestBody String requestBody) {
        JSONObject jsonObject = new JSONObject(requestBody);

        if (!jsonObject.has("id")) {
            List<String> errors = new ArrayList<>();
            errors.add("Missing id");
            return new RestResponseFail("Update fail", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);
        } else {
            Object object = jsonObject.get("id");
            if (!(object instanceof Number)) {
                List<String> errors = new ArrayList<>();
                errors.add("id must be a number");
                return new RestResponseFail("Update fail", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);
            }
        }

        User user = null;

        try {
            user = new User(jsonObject);
        } catch (InvalidProjectFinishingDateFormatException | NullUserPropertiesException ex) {
            ex.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            return new RestResponseFail("Update fail", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);
        }

        try {
            UserDto userDto = mapper.userToUserDto(userService.update(user));
            Map<String, Object> map = new HashMap<>();
            map.put("data", userDto);
            return new RestResponseSuccess(map, "Update successfully", HttpStatus.OK, HttpStatus.OK.value());
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            errors.add("No user with id: " + user.getId());
            return new RestResponseFail("Update fail", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);
        }
    }
}
