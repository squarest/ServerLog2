package com.example.chudofom.serverlog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Chudofom on 06.07.16.
 */
public interface Api {
    @POST("agent/login/")
    Call<LoginResponse> sendInf(@Body User user);
}