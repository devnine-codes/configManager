package com.aice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi<T> extends DaoJson {
    public static final String SUCCESS_STATUS = "success";
    public static final String FAIL_STATUS = "fail";
    public static final String ERROR_STATUS = "error";

    private String status;
    private String message;
    private T data;

    public static <T> ResponseApi<T> success(String message, T data) {
        return new ResponseApi<>(SUCCESS_STATUS, message, data);
    }

    public static <T> ResponseApi<T> fail(String message, T data) {
        return new ResponseApi<>(FAIL_STATUS, message, data);
    }

    public static <T> ResponseApi<T> error(String message, T data) {
        return new ResponseApi<>(ERROR_STATUS, message, data);
    }

}
