package com.warungposbespring.warungposbe.utils;

public class AppException extends RuntimeException {
    private int status = 400;
    private Object data;

    public AppException() {
        super("Something bad happen on app server please try again later, contact support for this error");
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public AppException(String message, int status) {
        super(message);
        this.status = status;
    }

    public AppException(String message, int status, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public static AppException create(String message) {
        return new AppException(message);
    }

    public static AppException create(String message, int status) {
        return new AppException(message, status);
    }

    public static AppException create(String message, Object data) {
        return new AppException(message, data);
    }

    public static AppException create(String message, int status, Object data) {
        return new AppException(message, status, data);
    }

    public JsonResponse<?> generateJsonResponse() {
        return JsonResponse.get(status, this.getMessage(), this.data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
