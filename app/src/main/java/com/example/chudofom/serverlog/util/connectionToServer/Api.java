package com.example.chudofom.serverlog.util.connectionToServer;

import com.example.chudofom.serverlog.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Chudofom on 06.07.16.
 */
public interface Api {
    @POST("agent/login/")
    Observable<LoginResponse> sendInf(@Body LoginRequest user);

    @GET("secret_user")
    Observable<User> getUser();

    @POST("secret_user")
    Observable<User> setUser(@Body User user);
}