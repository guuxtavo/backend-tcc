package com.ads.tcc.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private ZonedDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(){

    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
