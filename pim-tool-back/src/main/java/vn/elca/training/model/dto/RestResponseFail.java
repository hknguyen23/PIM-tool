package vn.elca.training.model.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public class RestResponseFail extends RestResponse {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public RestResponseFail(String message, HttpStatus status, int code, String error) {
        super(message, status, code);
        this.error = error;
    }
}
