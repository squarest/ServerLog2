package com.example.chudofom.serverlog.main;

/**
 * Created by Chudofom on 06.07.16.
 */
public interface MainView{
    String getId();
    String getPas();
    void showInf(String info);
    void showProgress();
    void hideProgress();

}
