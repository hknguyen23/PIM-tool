package vn.elca.training.model.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public class RestResponseFail extends RestResponse {
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public RestResponseFail(String message, HttpStatus status, int code, List<String> errors) {
        super(message, status, code);
        this.errors = errors;
    }
}
