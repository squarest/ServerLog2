package com.example.chudofom.serverlog.ConnectionToServer;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Chudofom on 06.07.16.
 */
public interface Api {
    @POST("agent/login/")
    Observable<LoginResponse> sendInf(@Body UserLogin user);
}