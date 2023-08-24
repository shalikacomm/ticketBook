package com.amanda.demotapApplication.beans;

import java.io.Serializable;

public class ResponseDto implements Serializable {

    private String code;
    private String message;
    private Object object;


    public ResponseDto() {
    }

    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(String code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
