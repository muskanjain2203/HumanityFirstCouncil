package com.example.humanityfirstcouncil.model;

public class CommonResponse {
    public int errorCode;
    public String errorMessage;

    public CommonResponse(){

    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
