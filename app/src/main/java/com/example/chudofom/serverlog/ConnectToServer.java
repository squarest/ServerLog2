package com.example.chudofom.serverlog;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chudofom on 07.07.16.
 */
public final class ConnectToServer {
    public static Api _connection = null;

    private void ConnectToServer() {
    }

    public static synchronized Api getInstance() {
        if (_connection == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://52.58.41.125:8000/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            _connection = retrofit.create(Api.class);
        }
        return _connection;
    }
}
