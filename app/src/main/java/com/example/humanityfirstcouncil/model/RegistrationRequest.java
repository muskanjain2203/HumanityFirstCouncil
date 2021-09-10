package com.example.humanityfirstcouncil.model;

public class RegistrationRequest extends LoginRequest {
    private String name;
    private String mobileNumber;
    private String country;
    private String bloodGroup;


    public RegistrationRequest(){

    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
