package com.example.chudofom.serverlog.activities.main;

import android.util.Log;

import com.example.chudofom.serverlog.util.connectionToServer.ConnectToServer;
import com.example.chudofom.serverlog.util.connectionToServer.LoginRequest;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        LoginRequest user = new LoginRequest("agitator", mainView.getId() + mainView.getPas(), "490fbfe28a7d157a");

        Subscription connection = ConnectToServer.getInstance().sendInf(user)
                .delay(5, TimeUnit.SECONDS)
                .map(obs -> obs.sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(()->mainView.hideProgress())
                .subscribe(sessionId -> {
                            mainView.showInf(sessionId + "");
                            Log.d("TAG", sessionId + "");
                        },
                        throwable ->
                        {
                            Log.d("TAG",throwable.getMessage());
                            mainView.showInf("all bad");
                        });
    }
}
