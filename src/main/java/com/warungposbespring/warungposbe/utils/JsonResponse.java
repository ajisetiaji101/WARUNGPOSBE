package com.warungposbespring.warungposbe.utils;

import lombok.ToString;

import java.util.Date;

/**
 * @author jafahr
 * email: kosso182@gmail.com
 * on 4/23/2020
 */
@ToString
public class JsonResponse<T> {
    public static final int OK_CODE = 200;
    public static final int OK_CREATED_CODE = 201;
    public static final int ERROR_CODE_REQUEST = 400;
    public static final int ERROR_CODE_SERVER = 500;
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private int status;
    private T data;
    private String message;
    private Date time = new Date();

    public JsonResponse() {
    }

    public JsonResponse(int status) {
        this.status = status;
    }

    public JsonResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> JsonResponse<T> ok() {
        return new JsonResponse<>(OK_CODE, null, SUCCESS);
    }

    public static <T> JsonResponse<T> ok(T data) {
        return new JsonResponse<>(OK_CODE, data, SUCCESS);
    }

    public static <T> JsonResponse<T> ok(String message) {
        return new JsonResponse<>(OK_CODE, null, message);
    }

    public static <T> JsonResponse<T> ok(String message, T data) {
        return new JsonResponse<>(OK_CODE, data, message);
    }

    public static <T> JsonResponse<T> error() {
        return new JsonResponse<>(ERROR_CODE_SERVER, null, ERROR);
    }

    public static <T> JsonResponse<T> error(String message) {
        return new JsonResponse<>(ERROR_CODE_SERVER, null, message);
    }

    public static <T> JsonResponse<T> error(String message, Integer status) {
        return new JsonResponse<>(status, null, message);
    }

    public static <T> JsonResponse<T> error(T data) {
        return new JsonResponse<>(ERROR_CODE_SERVER, data, ERROR);
    }

    public static <T> JsonResponse<T> error(String message, T data) {
        return new JsonResponse<>(ERROR_CODE_SERVER, data, message);
    }

    public static <T> JsonResponse<T> get(int status, String message, T data) {
        return new JsonResponse<>(status, data, message);
    }

    public boolean isSuccess() {
        return status == OK_CODE;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }
}
