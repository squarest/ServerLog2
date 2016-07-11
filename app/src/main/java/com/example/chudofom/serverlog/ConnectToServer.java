package com.example.chudofom.serverlog;

import retrofit2.http.Body;
import rx.Observable;

/**
 * Created by Chudofom on 07.07.16.
 */
public final class ConnectToServer {
    public static Api _connection = null;

    private void ConnectToServer() {
    }

    public static synchronized Api getInstance() {
        if (_connection == null) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://52.58.41.125:8000/")
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            _connection = retrofit.create(Api.class);
            Api fakeApi = new Api() {
                @Override
                public Observable<LoginResponse> sendInf(@Body User user) {
                    return Observable.just(null);
                }
            };
            _connection=fakeApi;
        }
        return _connection;
    }
}
