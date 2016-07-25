package com.example.chudofom.serverlog.connectionToServer;

import com.example.chudofom.serverlog.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by Chudofom on 06.07.16.
 */
public interface Api {
    @POST("agent/login/")
    Observable<LoginResponse> sendInf(@Body LoginRequest user);

    @GET("secret_user")
    Observable<User> getUser();

    @PUT("secret_user")
    Observable<User> setUser(@Body User user);
}