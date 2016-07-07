package com.example.chudofom.serverlog.main;

import android.util.Log;

import com.example.chudofom.serverlog.ConnectToServer;
import com.example.chudofom.serverlog.LoginResponse;
import com.example.chudofom.serverlog.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chudofom on 06.07.16.
 */
public class MainPresenter implements IMainPresenter {
    public final MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void butClicked() {
        mainView.showProgress();
        User user = new User("agitator", mainView.getId() + mainView.getPas(), "490fbfe28a7d157a");
        ConnectToServer.getInstance().createConnection()
                .sendInf(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mainView.hideProgress();
                Log.d("TAG", response.body().sessionId + "");
                mainView.showInf(response.body().sessionId + "");
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mainView.hideProgress();
                mainView.showInf("Все плохо");
                Log.d("TAG", t.getMessage() + "");
            }
        });
    }
}
