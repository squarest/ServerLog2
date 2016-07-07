package com.example.chudofom.serverlog.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityMainBinding;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;

public class MainActivity extends AppCompatActivity implements MainView {
    ActivityMainBinding binder;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter(this);
        binder.progressBar.setVisibility(ProgressBar.INVISIBLE);
        setListeners();

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

    public void loadingIsStopped (boolean b) {
        binder.setLoginbool(b);
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
