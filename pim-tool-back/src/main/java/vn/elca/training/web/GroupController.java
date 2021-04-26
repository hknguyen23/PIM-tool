package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Groupz;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.InvalidProjectFinishingDateFormatException;
import vn.elca.training.model.exception.NullProjectPropertiesException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.service.GroupService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController extends AbstractApplicationController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public RestResponse findAll() {
        List<GroupDto> groupDtos = groupService.findAll()
                .stream()
                .map(mapper::groupToGroupDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("data", groupDtos);
        return new RestResponseSuccess(
                map,
                "Total groups: " + groupDtos.size(),
                HttpStatus.OK,
                HttpStatus.OK.value()
        );
    }

    @PostMapping("/new")
    @ResponseBody
    public RestResponse newGroup(@RequestBody Object requestBody) {
        LinkedHashMap<String, Object> jsonObject = (LinkedHashMap<String, Object>)requestBody;

        Groupz groupz = new Groupz(jsonObject);
        GroupDto groupDto = mapper.groupToGroupDto(groupService.save(groupz));

        Map<String, Object> map = new HashMap<>();
        map.put("data", groupDto);
        return new RestResponseSuccess(map, "Create successfully", HttpStatus.OK, HttpStatus.OK.value());
    }
}
