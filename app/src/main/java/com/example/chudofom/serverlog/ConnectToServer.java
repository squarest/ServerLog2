package com.example.chudofom.serverlog;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chudofom on 07.07.16.
 */
public final class ConnectToServer {
    public static ConnectToServer _instance=null;
    public void ConnectToServer(){};

    public static synchronized ConnectToServer getInstance() {
        if (_instance == null)
            _instance = new ConnectToServer();
        return _instance;
    }
    public static Api createConnection()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.58.41.125:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Api.class);
    }
}
