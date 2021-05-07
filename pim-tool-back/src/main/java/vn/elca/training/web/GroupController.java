package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.*;
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
    public ResponseEntity<ResponseBodyDto> findAll() {
        List<GroupDto> groupDtos = groupService.findAll()
                .stream()
                .map(mapper::groupToGroupDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("groups", groupDtos);
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Total groups: " + groupDtos.size(),
                null,
                200,
                map
        );
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
