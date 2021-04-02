package com.example.myapplicationstructure.data_modal;


public class ResponceClass {

    String status;
    String message;
    int code;

    public ResponceClass(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public ResponceClass() {
    }

    public ResponceClass(String status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
