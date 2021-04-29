package vn.elca.training.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.dto.RestResponse;
import vn.elca.training.model.dto.RestResponseFail;
import vn.elca.training.model.dto.RestResponseSuccess;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Groupz;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.*;
import vn.elca.training.model.validator.ProjectValidator;
import vn.elca.training.service.EmployeeService;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private ProjectValidator projectValidator;

    @Autowired
    //@Qualifier("first")
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private EmployeeService employeeService;

//    @Autowired
//    public ProjectController(ProjectService projectService) {
//        this.projectService = projectService;
//    }

    @InitBinder("project")
    protected void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, true));
        //binder.setValidator(projectValidator);
    }

    @GetMapping("/")
    public RestResponse findAll(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "5") String pageSize,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String searchValue
    ) {
        Page<Project> paging = projectService.findAllWithPagination(
                Integer.parseInt(page),
                Integer.parseInt(pageSize),
                status,
                searchValue
        );

        List<ProjectDto> projectDtos = paging.getContent()
                .stream()
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("data", projectDtos);
        map.put("totalPages", paging.getTotalPages());
        map.put("currentPage", paging.getNumber());

        return new RestResponseSuccess(
                map,
                "Total projects: " + projectDtos.size(),
                HttpStatus.OK,
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/search")
    public RestResponse search(@RequestParam String keyword) {
        List<ProjectDto> projectDtos = projectService.findAll()
                .stream()
                .filter(project -> project.getProjectName().toLowerCase().contains(keyword.toLowerCase()))
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("Search result: ").append(projectDtos.size()).append(" project(s) with keyword '").append(keyword).append("' found");

        Map<String, Object> map = new HashMap<>();
        map.put("data", projectDtos);
        return new RestResponseSuccess(
                map,
                sb.toString(),
                HttpStatus.OK,
                HttpStatus.OK.value()
        );
    }

    //@Pattern(regexp = "\\d+")
    @GetMapping("/id/{idString}")
    public RestResponse findOne(@PathVariable String idString) {
        Long id = null;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Invalid id: " + idString,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );
        }

        try {
            Project project = projectService.findOne(id);
            Map<String, Object> map = new HashMap<>();
            map.put("data", mapper.projectToProjectDto(project));
            return new RestResponseSuccess(
                    map,
                    "Project found",
                    HttpStatus.OK,
                    HttpStatus.OK.value()
            );
        } catch (ProjectNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Can't find project with id: " + id,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );
        }
    }

    @PostMapping("/new")
    public RestResponse newProject(@RequestBody ProjectDto dto) {
        Long projectNumber = dto.getProjectNumber();
        boolean isExistProjectNumber = projectService.checkExistProjectNumber(projectNumber);
        if (isExistProjectNumber) {
            return new RestResponseFail(
                    "The project number already existed. Please select a different project number",
                    HttpStatus.NOT_FOUND,
                    101,
                    "The project number already existed"
            );
        }

        Project project = null;

        try {
            project = new Project(dto);
        } catch (InvalidProjectFinishingDateFormatException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Create fail",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );
        }

        project.setVersion(1L);

        Long groupID = (Long)dto.getGroup().get("id");
        try {
            Groupz groupz = groupService.findOne(groupID);
            project.setGroupz(groupz);
        } catch (GroupNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Create fail",
                    HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage()
            );
        }

        List<Long> employeeIds = dto.getEmployeeIds();
        Set<Employee> employees = project.getEmployees();
        for (Long employeeId : employeeIds) {
            try {
                Employee employee = employeeService.findOne(employeeId);
                employees.add(employee);
            } catch (EmployeeNotFoundException ex) {
                ex.printStackTrace();
                return new RestResponseFail("Create fail", HttpStatus.NOT_FOUND, 102, ex.getMessage());
            }
        }

        project.setEmployees(employees);

        ProjectDto projectDto = mapper.projectToProjectDto(projectService.save(project));
        Map<String, Object> map = new HashMap<>();
        map.put("data", projectDto);
        return new RestResponseSuccess(map, "Create successfully", HttpStatus.OK, HttpStatus.OK.value());
    }

    @PutMapping("/update")
    public RestResponse update(@RequestBody ProjectDto dto) {
        Project project = null;

        try {
            project = new Project(dto);
        } catch (InvalidProjectFinishingDateFormatException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Update fail",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );
        }

        Long groupID = (Long)dto.getGroup().get("id");
        try {
            Groupz groupz = groupService.findOne(groupID);
            project.setGroupz(groupz);
        } catch (GroupNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Update fail",
                    HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage()
            );
        }

        List<Long> employeeIds = dto.getEmployeeIds();
        Set<Employee> employees = new HashSet<>();
        for (Long employeeId : employeeIds) {
            try {
                Employee employee = employeeService.findOne(employeeId);
                employees.add(employee);
            } catch (EmployeeNotFoundException ex) {
                ex.printStackTrace();
                return new RestResponseFail("Update fail", HttpStatus.NOT_FOUND, 102, ex.getMessage());
            }
        }

        project.setEmployees(employees);

        Long version = dto.getVersion();

        try {
            ProjectDto projectDto = mapper.projectToProjectDto(projectService.update(project, version));
            Map<String, Object> map = new HashMap<>();
            map.put("data", projectDto);
            return new RestResponseSuccess(map, "Update successfully", HttpStatus.OK, HttpStatus.OK.value());
        } catch (ProjectNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Update fail",
                    HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    "No project with id: " + project.getId()
            );
        } catch (ProjectVersionNotMatched ex) {
            ex.printStackTrace();
            return new RestResponseFail("Update fail", HttpStatus.NOT_FOUND, 103, ex.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public RestResponse deleteProject(@RequestBody List<Long> projectIds) {
        try {
            for (Long projectId : projectIds) {
                projectService.deleteById(projectId);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("data", null);
            return new RestResponseSuccess(map, "Delete successfully", HttpStatus.OK, HttpStatus.OK.value());
        } catch (ProjectNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Delete fail",
                    HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage()
            );
        }
    }

    @PostMapping("/createMaintenanceProject")
    public RestResponse createMaintenanceProject(@RequestBody String requestBody) {
        JSONObject jsonObject = new JSONObject(requestBody);
        if (!jsonObject.has("id")) {
            return new RestResponseFail(
                    "Create fail",
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    "Missing id"
            );
        } else {
            Object object = jsonObject.get("id");
            if (!(object instanceof Number)) {
                return new RestResponseFail(
                        "Create fail",
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value(),
                        "id must be a number"
                );
            }
        }

        Long id = jsonObject.getLong("id");

        try {
            Project oldProject = projectService.findOne(id);
            Project newProject = projectService.createMaintenanceProject(oldProject);

            List<ProjectDto> projectDtos = new ArrayList<>();

            if (newProject != null) {
                oldProject.setActivated(false);
                oldProject = projectService.update(oldProject, 1L);

                projectDtos.add(mapper.projectToProjectDto(newProject));
                projectDtos.add(mapper.projectToProjectDto(oldProject));

                Map<String, Object> map = new HashMap<>();
                map.put("data", projectDtos);
                return new RestResponseSuccess(map, "Create successfully", HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return new RestResponseFail(
                        "Something wrong happened!!!",
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value(),
                        "Something wrong happened!!!"
                );
            }
        } catch (ProjectNotFoundException ex) {
            ex.printStackTrace();
            return new RestResponseFail(
                    "Can't find project with id: " + id,
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );
        }
    }
}
