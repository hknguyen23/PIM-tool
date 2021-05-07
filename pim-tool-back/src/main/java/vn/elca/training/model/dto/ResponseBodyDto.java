package vn.elca.training.model.dto;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ResponseBodyDto {
    private String message;
    private List<String> errors;
    private int code;
    private Map<String, Object> data;

    public ResponseBodyDto(String message, List<String> errors, int code, Map<String, Object> data) {
        this.message = message;
        this.errors = errors;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
