package vn.elca.training.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.elca.training.model.dto.ResponseBodyDto;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ResponseBodyDto> handleGroupNotFoundException(GroupNotFoundException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                ex.getMessage(),
                errors,
                404,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ResponseBodyDto> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                ex.getMessage(),
                errors,
                404,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ResponseBodyDto> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Project Not Found",
                errors,
                404,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteProjectException.class)
    public ResponseEntity<ResponseBodyDto> handleDeleteProjectException(DeleteProjectException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Delete fail",
                errors,
                400,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNumberAlreadyExistsException.class)
    public ResponseEntity<ResponseBodyDto> handleProjectNumberAlreadyExistsException(ProjectNumberAlreadyExistsException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                ex.getMessage(),
                errors,
                101,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectVersionNotMatchedException.class)
    public ResponseEntity<ResponseBodyDto> handleProjectVersionNotMatchedException(ProjectVersionNotMatchedException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseBodyDto responseBody = new ResponseBodyDto(
                "Update fail",
                errors,
                103,
                null
        );
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
