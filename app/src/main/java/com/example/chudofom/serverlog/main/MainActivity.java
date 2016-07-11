package com.example.chudofom.serverlog.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityMainBinding;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;

public class MainActivity extends AppCompatActivity implements MainView {
    ActivityMainBinding binder;
    MainPresenter presenter;
    RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter(this);
        rotate();
        setListeners();

    }

    public void rotate() {
        rotateAnimation = new RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(700);
    }

    void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public String getId() {
        return binder.idEmail.getText().toString();
    }

    @Override
    public String getPas() {
        return binder.editPassword.getText().toString();
    }

    @Override
    public void showInf(String info) {
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgress() {

        binder.setLoginbool(false);
        hideKeyboard(binder.view);
        binder.rotateShape.startAnimation(rotateAnimation);

    }

    @Override
    public void hideProgress() {
        binder.setLoginbool(true);
        hideKeyboard(binder.view);
        binder.rotateShape.setAnimation(null);
    }


    private void setListeners() {
        binder.button.setOnClickListener(v -> presenter.butClicked());
        Observable<Boolean> idValidation = RxTextView.textChanges(binder.idEmail)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.length() == 7);
        Observable<Boolean> pasValidation = RxTextView.textChanges(binder.editPassword)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.length() == 7);
        Observable.combineLatest(idValidation, pasValidation, (a, b) -> a && b)
                .subscribe(b -> {
                    binder.setLoginbool(b);
                    binder.setAddcompleted(b);
                });
    }

}
