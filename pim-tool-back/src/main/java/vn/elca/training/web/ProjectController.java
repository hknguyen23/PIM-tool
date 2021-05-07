package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.*;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enumerator.ProjectStatuses;
import vn.elca.training.model.exception.*;
import vn.elca.training.service.EmployeeService;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDto> findAll(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "5") String pageSize,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String searchValue
    ) {
        int limit = Integer.parseInt(pageSize);
        int offset = Integer.parseInt(page) * limit;
        Page<Project> paging = projectService.findAllWithPagination(
                offset,
                limit,
                status,
                searchValue
        );

        List<ProjectDto> projectDtos = paging.getContent()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("projects", projectDtos);
        double totalPages;

        if (StringUtils.isEmpty(status)) {
            totalPages = Math.ceil((double)projectService.count() / limit);
        } else {
            totalPages = Math.ceil((double)projectService.countByStatus(ProjectStatuses.valueOf(status)) / limit);
        }

        map.put("totalPages", (int)totalPages);
        map.put("currentPage", offset / limit);

        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Successfully fetching data",
                null,
                200,
                map
        );

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseBodyDto> search(@RequestParam String keyword) {
        List<ProjectDto> projectDtos = projectService.findAllByProjectName(keyword)
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("projects", projectDtos);
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Total projects found: " + projectDtos.size(),
                null,
                200,
                map
        );

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBodyDto> findOne(@PathVariable Long id) throws ProjectNotFoundException {
        Project project = projectService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("project", mapper.projectToProjectDto(project));
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Project found",
                null,
                200,
                map
        );
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private Project setUpProjectFromRequest(ProjectDto dto) {
        Project project = mapper.projectDtoToProject(dto);

        Long groupID = dto.getGroup().getId();
        Group group = groupService.findOne(groupID);
        project.setGroup(group);

        List<Long> employeeIds = dto.getEmployeeIds();
        Set<Employee> employees = employeeService.findAllByListIds(employeeIds);
        project.setEmployees(employees);

        return project;
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseBodyDto> newProject(@RequestBody @Valid ProjectDto dto, BindingResult bindingResult)
        throws GroupNotFoundException, ProjectNumberAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            // parse errors and send back to user
            List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError)error).getField();
                errors.add(fieldName + ": " + error.getDefaultMessage());
            });
            ResponseBodyDto responseBody = new ResponseBodyDto(
                    "Create fail",
                    errors,
                    500,
                    null
            );
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        Project project = setUpProjectFromRequest(dto);

        ProjectDto projectDto = mapper.projectToProjectDto(projectService.save(project));
        Map<String, Object> map = new HashMap<>();
        map.put("project", projectDto);
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Create successfully",
                null,
                200,
                map
        );
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBodyDto> update(@RequestBody @Valid ProjectDto dto, BindingResult bindingResult)
        throws GroupNotFoundException, ProjectNotFoundException, ProjectVersionNotMatchedException {
        if (bindingResult.hasErrors()) {
            // parse errors and send back to user
            List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError)error).getField();
                errors.add(fieldName + ": " + error.getDefaultMessage());
            });
            ResponseBodyDto responseBody = new ResponseBodyDto(
                    "Update fail",
                    errors,
                    500,
                    null
            );
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        Project project = setUpProjectFromRequest(dto);

        ProjectDto projectDto = mapper.projectToProjectDto(projectService.update(project));
        Map<String, Object> map = new HashMap<>();
        map.put("project", projectDto);
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Update successfully",
                null,
                200,
                map
        );
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBodyDto> deleteProjects(@RequestBody List<Long> projectIds)
        throws ProjectNotFoundException, DeleteProjectException {
        projectService.deleteListIds(projectIds);

        Map<String, Object> map = new HashMap<>();
        map.put("data", null);
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Delete successfully",
                null,
                200,
                map
        );

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
