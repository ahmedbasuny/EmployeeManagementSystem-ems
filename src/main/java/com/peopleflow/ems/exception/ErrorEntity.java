package com.peopleflow.ems.exception;

public class ErrorEntity {
    String error;

    public ErrorEntity(String error) {
        this.error = error;
    }

    public ErrorEntity() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
