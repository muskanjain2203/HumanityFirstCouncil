package com.example.humanityfirstcouncil.retrofit;

import com.example.humanityfirstcouncil.model.GoalsRequest;
import com.example.humanityfirstcouncil.model.GoalsResponse;
import com.example.humanityfirstcouncil.model.LoginRequest;
import com.example.humanityfirstcouncil.model.LoginResponse;
import com.example.humanityfirstcouncil.model.RegistrationRequest;
import com.example.humanityfirstcouncil.model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServices {
    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("registration.php")
    Call<RegistrationResponse> registration(@Body RegistrationRequest request);

    @POST("fetch_goals.php")
    Call<GoalsResponse> goals(@Body GoalsRequest request);

}
