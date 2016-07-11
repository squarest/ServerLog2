package com.example.chudofom.serverlog.main;

import com.example.chudofom.serverlog.User;

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

        //ConnectToServer.getInstance().sendInf(user).enqueue(new Callback<LoginResponse>() {
          //  @Override
            //public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
              //  if (response.body().sessionId != null) {
                //    mainView.hideProgress();
                  //  mainView.showInf(response.body().sessionId + "");
                    //Log.d("TAG", response.body().sessionId + "");
                //}
            //}

            //@Override
            //public void onFailure(Call<LoginResponse> call, Throwable t) {
               // mainView.hideProgress();
            //    mainView.showInf("Все плохо");
              //  Log.d("TAG", t.getMessage() + "");
        //    }
        //});

    }
}
