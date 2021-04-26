package vn.elca.training.model.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class RestResponse {
    protected String message;
    protected HttpStatus status;
    protected int code;

    protected RestResponse(String message, HttpStatus status, int code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.code = status.value();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
