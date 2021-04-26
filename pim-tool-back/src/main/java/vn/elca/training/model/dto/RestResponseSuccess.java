package vn.elca.training.model.dto;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestResponseSuccess extends RestResponse {
    private Map<String, Object> map;

    public Object getData() {
        return map;
    }

    public void setData(Map<String, Object> map) {
        this.map = map;
    }

    public RestResponseSuccess(Map<String, Object> map, String message, HttpStatus status, int code) {
        super(message, status, code);
        this.map = map;
    }
}
